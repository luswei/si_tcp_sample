package com.neriudon.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.integration.ip.util.TestingUtilities;
import org.springframework.integration.test.util.SocketUtils;

import com.neriudon.example.gateway.SimpleGateway;

public final class Main {

	public static void main(final String... args) {
		new Main().startTCPConnection();
	}

	public void startTCPConnection() {
		final Scanner scanner = new Scanner(System.in);

		final GenericXmlApplicationContext context = Main.setupContext();
		final SimpleGateway gateway = context.getBean(SimpleGateway.class);

		final AbstractServerConnectionFactory factory1 = context.getBean("server1",
				AbstractServerConnectionFactory.class);
		final AbstractServerConnectionFactory factory2 = context.getBean("server2",
				AbstractServerConnectionFactory.class);

		System.out.print("Waiting for server to accept connections...");
		TestingUtilities.waitListening(factory1, 10000L);
		TestingUtilities.waitListening(factory2, 10000L);
		System.out.println("running.\n\n");

		System.out.println("Please enter some text and press <enter>: ");
		System.out.println("\tNote:");
		System.out.println("\t- Entering FAIL will create an exception");
		System.out.println("\t- Entering q will quit the application");
		System.out.print("\n");
		System.out.println("\t--> Please also check out the other samples, " + "that are provided as JUnit tests.");
		System.out.println("\t--> You can also connect to the servers on port '" + factory1.getPort() + " and "
				+ factory2.getPort() + "' using Telnet.\n\n");

		while (true) {
			final String input = scanner.nextLine();
			if ("q".equals(input.trim())) {
				break;
			} else {
				final String result = gateway.send(input);
				System.out.println(result);
			}
		}
		scanner.close();
		System.out.println("Exiting application...bye.");
		System.exit(0);
	}

	public static GenericXmlApplicationContext setupContext() {
		final GenericXmlApplicationContext context = new GenericXmlApplicationContext();

		System.out.print("Detect open server socket...");
		int availableServerSocket1 = SocketUtils.findAvailableServerSocket(50001);
		int availableServerSocket2 = SocketUtils.findAvailableServerSocket(50002);

		final Map<String, Object> sockets = new HashMap<String, Object>();
		sockets.put("availableServerSocket1", availableServerSocket1);
		sockets.put("availableServerSocket2", availableServerSocket2);

		final MapPropertySource propertySource = new MapPropertySource("sockets", sockets);

		context.getEnvironment().getPropertySources().addLast(propertySource);

		System.out.println("using port " + context.getEnvironment().getProperty("availableServerSocket") + " and "
				+ context.getEnvironment().getProperty("availableServerSocket"));

		context.load("classpath:META-INF/spring/integration/tcp_sample_context.xml");
		context.registerShutdownHook();
		context.refresh();

		return context;
	}
}
