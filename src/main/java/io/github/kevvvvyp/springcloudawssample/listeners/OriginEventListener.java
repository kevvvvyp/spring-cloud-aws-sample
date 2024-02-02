package io.github.kevvvvyp.springcloudawssample.listeners;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import io.github.kevvvvyp.springcloudawssample.transfer.OriginEvent;
import io.github.kevvvvyp.springcloudawssample.transfer.SnsMessage;
import io.github.kevvvvyp.springcloudawssample.transfer.TxEvent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OriginEventListener {

	private final ObjectMapper objectMapper;
	private final SqsTemplate sqsTemplate;

	public OriginEventListener( ObjectMapper objectMapper, SqsTemplate sqsTemplate ) {
		this.objectMapper = objectMapper;
		this.sqsTemplate = sqsTemplate;
	}

	@SqsListener(value = "my-queue")
	public void receiveEvent( @Valid @NonNull final SnsMessage snsMessage ) {
		log.info( "Received SnsMessage: {}", snsMessage );
		final OriginEvent originEvent = deserialiseMessageField( snsMessage );

		// Perform some transformation
		final TxEvent txEvent = new TxEvent( originEvent.text() );

		// Publish
		log.info( "Publishing TxEvent: {}", txEvent );
		sqsTemplate.send( "my-fifo.fifo", txEvent );
	}

	private OriginEvent deserialiseMessageField( final SnsMessage snsMessage ) {
		try {
			final String message = snsMessage.getMessage();
			return objectMapper.readValue( message, OriginEvent.class );
		} catch ( JsonProcessingException e ) {
			throw new RuntimeException( "Failed to parse SnsMessage.message", e );
		}
	}

}
