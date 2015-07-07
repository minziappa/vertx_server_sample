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

public class SockJsServer {

	final Logger logger = LoggerFactory.getLogger(SockJsServer.class);

	private Vertx vertx = null;
	public static EventBus eb = null;

	public void init() {

		System.out.println("init - start");

		vertx = VertxFactory.newVertx();
        HttpServer server = vertx.createHttpServer();

        // Set security permission to let everything go through
        JsonArray permitted = new JsonArray();
        permitted.add(new JsonObject());

        // Create SockJS and bridge it to the Event Bus
        SockJSServer sockJSServer = vertx.createSockJSServer(server);
        sockJSServer.bridge(new JsonObject().putString("prefix", "/eventbus"), permitted, permitted);

        eb = vertx.eventBus();
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
        server.listen(8091, "172.17.94.120");

		System.out.println("init - end");
	}

//	
//	public long getCharge() throws Exception  {
//
//		List<Object> clientIdList = (List<Object>)configuration.getList("realTime.clientId");
//
//		long longChargeSum = 0;
//
//		for (Object clientId : clientIdList) {
//
//			StringBuffer sb = new StringBuffer();
//			sb.append("https://api-patriot-bridge.tools.ndca.jp/onix/").append(clientId).append("/charge/daily/2014-02-13:2014-02-13?access_token=909a3bb4-a24b-4b2a-b8a3-5cfb370a6d1f");
//
//			String json = ApiHttpClient.reqGetMethod(null, sb.toString());
//
//			JsonObject joMessage = new JsonObject(json);
//			String strJson = (String) joMessage.getObject("result").getArray("data").get(0);
//			JsonObject jsonCharge = new JsonObject(strJson);
//			longChargeSum = longChargeSum + jsonCharge.getLong("charge").longValue();
//		}
//
//		return longChargeSum;
//	}

	public void destory() {
		System.out.println("destory - start");
		vertx.stop();
		System.out.println("destory - end");
	}

}