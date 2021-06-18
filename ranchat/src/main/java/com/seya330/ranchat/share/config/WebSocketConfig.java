package com.seya330.ranchat.share.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer{
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		//해당 api 를 구독하고 있는 클라이언트 에게 메세지 전달.
		registry.enableSimpleBroker("/sub");
		//클라이언트로부터 메세지를 받을 api
		registry.setApplicationDestinationPrefixes("/msg");
	}
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// TODO Auto-generated method stub
		registry.addEndpoint("/endPoint").setAllowedOrigins("*").withSockJS();
	}	
}
