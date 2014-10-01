package io.sample.vertx.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.VertxFactory;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.sockjs.SockJSServer;

public class TwoSockJsServer {

	final Logger logger = LoggerFactory.getLogger(TwoSockJsServer.class);

	private Vertx vertx = null;
//	protected static PlatformManagerInternal platformManager = (PlatformManagerInternal)PlatformLocator.factory.createPlatformManager();
//	protected Vertx vertx = platformManager.vertx();

	public void init() {

		System.out.println("init - start");

		vertx = VertxFactory.newVertx();

        HttpServer server = vertx.createHttpServer();
        // Create SockJS and bridge it to the Event Bus
        SockJSServer sockJSServer = vertx.createSockJSServer(server);

        // Start the server
        server.listen(8092, "172.28.153.116");

        System.out.println("init - end");
	}

	public void destory() {
		System.out.println("init - start");
		vertx.stop();
		System.out.println("destory - end");
	}

}