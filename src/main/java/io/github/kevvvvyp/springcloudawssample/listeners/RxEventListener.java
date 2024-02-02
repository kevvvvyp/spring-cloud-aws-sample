package io.github.kevvvvyp.springcloudawssample.listeners;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import io.awspring.cloud.sqs.annotation.SqsListener;
import io.github.kevvvvyp.springcloudawssample.transfer.TxEvent;
import io.github.kevvvvyp.springcloudawssample.transfer.RxEvent;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RxEventListener {

	// This doesn't work, but should we ought to just map JSON?
	@SqsListener(value = "my-fifo.fifo")
	public void receiveEvent( @Valid @NonNull final RxEvent rxEvent ) {
		log.info( "Success! Received: {}", rxEvent );
	}

	// This works fine (assuming fifo has been drained of previous messages (kill docker container))
	//	@SqsListener(value = "my-fifo.fifo")
	//	public void receiveEvent( @Valid @NonNull final TxEvent txEvent ) {
	//		log.info( "Success! Received: {}", txEvent );
	//	}
}
