package com.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
/*/chat → This is where the client connects initially using SockJS.
/app → This prefix matches the @MessageMapping destination.
/topic → This prefix matches the @SendTo destination.*/
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/topic");                //for subscribing 
		config.setApplicationDestinationPrefixes("/app");  // prefix for sending message 
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		//URL for connection establishment
		registry.addEndpoint("/chat").setAllowedOrigins("http://localhost:5173").withSockJS(); 
	}

}
