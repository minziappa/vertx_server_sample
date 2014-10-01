package io.sample.vertx.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.VertxFactory;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.core.sockjs.SockJSServer;
import org.vertx.java.core.eventbus.Message;

public class CopyOfSockJsServer {

	final Logger logger = LoggerFactory.getLogger(CopyOfSockJsServer.class);

	private Vertx vertx = null;
	public static EventBus eb = null;

	public void init() {

		System.out.println("init - start");

		vertx = VertxFactory.newVertx();
        HttpServer server = vertx.createHttpServer();

//        // Register HTTP handler
//        server.requestHandler(new Handler<HttpServerRequest>() {
//                @Override
//                public void handle(HttpServerRequest req) {
//                        String file = req.path().equals("/") ? "index.html" : req.path();
//                        req.response().sendFile("webroot/" + file);
//                }
//
//        });

        // Set security permission to let everything go through
        JsonArray permitted = new JsonArray();
        permitted.add(new JsonObject());

        // Create SockJS and bridge it to the Event Bus
        SockJSServer sockJSServer = vertx.createSockJSServer(server);
        sockJSServer.bridge(new JsonObject().putString("prefix", "/eventbus"), permitted, permitted);

        eb = vertx.eventBus();

//        // Register Handler 1
//        eb.registerLocalHandler("app.conduit", new Handler<Message<JsonObject>>() {
//                @Override
//                public void handle(Message<JsonObject> message) {
//                	logger.info("Handler 1 (Local) received: " + message.body().toString());
//                }
//         });

    	logger.info("Just test 0");

        // Register Handler 2
        eb.registerHandler("app.conduit", new Handler<Message<JsonObject>>() {
            @Override
            public void handle(Message<JsonObject> message) {
            	logger.info("Handler 2 (Shared) received: " + message.body().toString());

                JsonObject joMessage = new JsonObject();
                joMessage.putString("text", "{\"x\":" + 0 + ", \"y\":" + 0 + "}");

            	message.reply(joMessage);
            }
        });

    	logger.info("Just test 3");

        // Start the server
        server.listen(8091, "172.28.153.116");

		System.out.println("init - end");
	}

//	
//	public EventBus getEventBus() {
//		return eb;
//	}

	public void destory() {
		System.out.println("destory - start");
		vertx.stop();
		System.out.println("destory - end");
	}

}