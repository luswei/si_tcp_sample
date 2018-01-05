package com.neriudon.example.tcp.interceptor;

import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.integration.ip.tcp.connection.TcpConnectionInterceptorFactory;
import org.springframework.integration.ip.tcp.connection.TcpConnectionInterceptorSupport;

public class SimpleInterceptorFactory extends ApplicationObjectSupport implements TcpConnectionInterceptorFactory {

	public SimpleInterceptorFactory() {
	}

	@Override
	public TcpConnectionInterceptorSupport getInterceptor() {
		return new SimpleInterceptor(getApplicationContext());
	}
}
