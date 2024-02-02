package io.github.kevvvvyp.springcloudawssample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestSpringCloudAwsSampleApplication {

	public static void main( String[] args ) {
		SpringApplication.from( SpringCloudAwsSampleApplication::main )
				.with( TestSpringCloudAwsSampleApplication.class )
				.run( args );
	}

}
