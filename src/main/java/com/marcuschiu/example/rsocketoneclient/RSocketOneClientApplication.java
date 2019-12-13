package com.marcuschiu.example.rsocketoneclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.rsocket.RSocketRequester;

@SpringBootApplication
public class RSocketOneClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(RSocketOneClientApplication.class, args);
	}

	@Bean
	RSocketRequester rSocketRequester(RSocketRequester.Builder rsocketRequesterBuilder) {
		return rsocketRequesterBuilder
//				.connect(ClientTransport)
//				.connectWebSocket(URI)
				.connectTcp("localhost", 7001)
				.retry() // auto reconnects to server
//				.cache()
//				.checkpoint()
				.block();
	}
}
