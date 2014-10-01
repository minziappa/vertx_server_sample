package io.sample.vertx.server;

import org.vertx.java.core.Handler;
import org.vertx.java.core.net.NetSocket;
import org.vertx.java.core.streams.Pump;
import org.vertx.java.platform.Verticle;

public class EchoServer extends Verticle {

	public void start() {

		System.out.println("This is start");

		vertx.createNetServer().connectHandler(
			new Handler<NetSocket>() {
				public void handle(final NetSocket socket) {
					Pump.createPump(socket, socket).start();
				}
			}
		).listen(8085);

	}

	public void stop() {
		System.out.println("This is stop");
		vertx.stop();
	}

}