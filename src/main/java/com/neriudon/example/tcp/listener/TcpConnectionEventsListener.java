package com.neriudon.example.tcp.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.integration.ip.tcp.connection.TcpConnectionCloseEvent;
import org.springframework.integration.ip.tcp.connection.TcpConnectionExceptionEvent;
import org.springframework.integration.ip.tcp.connection.TcpConnectionOpenEvent;
import org.springframework.stereotype.Component;

@Component
public class TcpConnectionEventsListener {

	private static final Logger logger = LoggerFactory.getLogger(TcpConnectionEventsListener.class);

	@EventListener
	public void onTcpConnectionOpenEvent(TcpConnectionOpenEvent event) {
		logger.debug("★OPEN★ {} ", event);
	}

	@EventListener
	public void onTcpConnectionCloseEvent(TcpConnectionCloseEvent event) {
		logger.debug("★CLOSE★ {} ", event);
	}

	@EventListener
	public void onTcpConnectionExceptionEvent(TcpConnectionExceptionEvent event) {
		logger.debug("★ERROR★ {} ", event);
	}
}
