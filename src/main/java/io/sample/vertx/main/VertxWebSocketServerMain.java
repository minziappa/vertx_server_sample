package io.sample.vertx.main;

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class VertxWebSocketServerMain implements Daemon {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("destory");
	}

	@Override
	public void init(DaemonContext arg0) throws DaemonInitException, Exception {
		// TODO Auto-generated method stub
		System.out.println("start >>> init");
		new ClassPathXmlApplicationContext("springConfig.xml");
		System.out.println("stop >>> init");

	}

	@Override
	public void start() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("start");
	}

	@Override
	public void stop() throws Exception {
		System.out.println("stop");
	}

}
