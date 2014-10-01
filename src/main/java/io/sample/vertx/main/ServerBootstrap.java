package io.sample.vertx.main;

import org.springframework.stereotype.Component;
import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.VertxFactory;
import org.vertx.java.core.net.NetSocket;
import org.vertx.java.core.streams.Pump;

@Component
public class ServerBootstrap {

	public void init() throws Exception {

		System.out.println("init is start....");

	    Vertx vertx = VertxFactory.newVertx();
	    // Create an echo server
	    vertx.createNetServer().connectHandler(new Handler<NetSocket>() {
	    	public void handle(final NetSocket socket) {
	    		Pump.createPump(socket, socket).start();
	    	}
	    }).listen(8091);

	    // Prevent the JVM from exiting
	    System.in.read();

	    System.out.println("init is end....");

	}

	// @PreDestroy
	public void destroy() {
		System.out.println("destory");
	}

}