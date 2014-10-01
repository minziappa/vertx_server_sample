package io.sample.vertx.server;

import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.VertxFactory;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.ServerWebSocket;

public class WebSocketServer {

	private Vertx vertx = null;

	public void init() {

		System.out.println("init - start");

		vertx = VertxFactory.newVertx();
		HttpServer server = vertx.createHttpServer();

		server.websocketHandler(new Handler<ServerWebSocket>() {

			public void handle(final ServerWebSocket ws) {
				if (ws.path().equals("/myapp")) {

					ws.dataHandler(
						new Handler<Buffer>() {
							public void handle(Buffer data) {
								ws.writeTextFrame(data.toString()); // Echo it back
								System.out.println("data>>>>"+ data.toString());
							}
						}
					);

				} else {
					ws.reject();
				}
			}

		}).requestHandler(new Handler<HttpServerRequest>() {
			public void handle(HttpServerRequest req) {

				// Set the path of mapping.
				if (req.path().equals("/")) {
					req.response().sendFile("websockets/ws.html"); // Serve the html
				}

			}
		}).listen(8091, "172.28.153.116");
		System.out.println("init - end");
	}

	public void destory() {
		System.out.println("init - start");
		vertx.stop();
		System.out.println("destory - end");
	}

}