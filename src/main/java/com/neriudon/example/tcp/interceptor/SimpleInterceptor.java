package com.neriudon.example.tcp.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.integration.ip.tcp.connection.TcpConnectionInterceptorSupport;
import org.springframework.messaging.Message;

public class SimpleInterceptor extends TcpConnectionInterceptorSupport {

	private static final Logger logger = LoggerFactory.getLogger(SimpleInterceptor.class);
	
	public SimpleInterceptor(ApplicationEventPublisher publisher) {
		super(publisher);
	}

	@Override
	public void send(Message<?> message) throws Exception {
		logger.debug("send message via interceptor");
		super.send(message);
	}
}
