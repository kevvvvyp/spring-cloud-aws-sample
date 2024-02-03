package io.github.kevvvvyp.springcloudawssample;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;

import io.awspring.cloud.autoconfigure.sqs.SqsProperties;
import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory;
import io.awspring.cloud.sqs.listener.ContainerOptionsBuilder;
import io.awspring.cloud.sqs.listener.errorhandler.AsyncErrorHandler;
import io.awspring.cloud.sqs.listener.errorhandler.ErrorHandler;
import io.awspring.cloud.sqs.listener.interceptor.AsyncMessageInterceptor;
import io.awspring.cloud.sqs.listener.interceptor.MessageInterceptor;
import io.awspring.cloud.sqs.support.converter.SqsHeaderMapper;
import io.awspring.cloud.sqs.support.converter.SqsMessagingMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;

import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@Configuration
public class MessagingConfig {

	/**
	 * Defines a custom message converter to map JSON SQS messages to POJOs.
	 */
	@Bean
	public MessageConverter jackson2MessageConverter( final ObjectMapper objectMapper ) {
		final MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setObjectMapper( objectMapper );
		converter.setSerializedPayloadClass( String.class );
		converter.setStrictContentTypeMatch( false );
		return converter;
	}

	@Bean
	public SqsMessagingMessageConverter sqsMessagingMessageConverter(
			final MessageConverter jackson2MessageConverter ) {
		// Create converter instance
		SqsMessagingMessageConverter messageConverter = new SqsMessagingMessageConverter();

		// Configure Type Header
		messageConverter.setPayloadTypeHeader( "myTypeHeader" );

		// Configure Header Mapper
		SqsHeaderMapper headerMapper = new SqsHeaderMapper();
		headerMapper.setAdditionalHeadersFunction(
				( ( sqsMessage, accessor ) -> accessor.toMessageHeaders() ) );
		messageConverter.setHeaderMapper( headerMapper );

		// Configure Payload Converter
		messageConverter.setPayloadMessageConverter( jackson2MessageConverter );
		return messageConverter;
	}

	@Bean
	@Primary
	public SqsMessageListenerContainerFactory<Object> defaultSqsListenerContainerFactory(
			SqsMessagingMessageConverter messageConverter, SqsProperties sqsProperties,
			ObjectProvider<SqsAsyncClient> sqsAsyncClient,
			ObjectProvider<AsyncErrorHandler<Object>> asyncErrorHandler,
			ObjectProvider<ErrorHandler<Object>> errorHandler,
			ObjectProvider<AsyncMessageInterceptor<Object>> asyncInterceptors,
			ObjectProvider<MessageInterceptor<Object>> interceptors ) {

		SqsMessageListenerContainerFactory<Object> factory = new SqsMessageListenerContainerFactory<>();
		factory.configure( sqsContainerOptionsBuilder -> configureContainerOptions( sqsProperties,
				sqsContainerOptionsBuilder ) );
		sqsAsyncClient.ifAvailable( factory::setSqsAsyncClient );
		asyncErrorHandler.ifAvailable( factory::setErrorHandler );
		errorHandler.ifAvailable( factory::setErrorHandler );
		interceptors.forEach( factory::addMessageInterceptor );
		asyncInterceptors.forEach( factory::addMessageInterceptor );

		// Set MessageConverter to the factory or container
		factory.configure( options -> options.messageConverter( messageConverter ) );
		return factory;
	}

	private void configureContainerOptions( SqsProperties sqsProperties,
			ContainerOptionsBuilder<?, ?> options ) {
		PropertyMapper mapper = PropertyMapper.get().alwaysApplyingWhenNonNull();
		mapper.from( sqsProperties.getListener().getMaxConcurrentMessages() )
				.to( options::maxConcurrentMessages );
		mapper.from( sqsProperties.getListener().getMaxMessagesPerPoll() )
				.to( options::maxMessagesPerPoll );
		mapper.from( sqsProperties.getListener().getPollTimeout() ).to( options::pollTimeout );
	}

}
