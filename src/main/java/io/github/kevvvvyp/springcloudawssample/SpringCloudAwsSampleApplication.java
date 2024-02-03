package io.github.kevvvvyp.springcloudawssample;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import io.awspring.cloud.sns.core.SnsTemplate;
import io.github.kevvvvyp.springcloudawssample.transfer.OriginEvent;
import io.github.kevvvvyp.springcloudawssample.transfer.SnsMessage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class SpringCloudAwsSampleApplication {

	public static void main( String[] args ) {
		final ConfigurableApplicationContext ctx = SpringApplication.run(
				SpringCloudAwsSampleApplication.class, args );

		final OriginEvent originEvent = new OriginEvent( "GREEN" );

		final SnsTemplate template = ctx.getBean( SnsTemplate.class );

		log.info( "Publishing OriginEvent: {}", originEvent );
		template.convertAndSend( "my-topic", originEvent );
	}

}
