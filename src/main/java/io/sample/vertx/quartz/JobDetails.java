package io.sample.vertx.quartz;

import io.sample.vertx.server.SockJsServer;
import io.sample.vertx.service.SocketService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.vertx.java.core.json.JsonObject;

public class JobDetails {

	final Logger logger = LoggerFactory.getLogger(JobDetails.class);

	@Autowired
	SocketService  socketService;

	public JobDetails() {
	}

	public void executeJob() {

		try {

			logger.info("start");

			JsonObject joMessage = new JsonObject();

			String jsonMessage = socketService.getSampleOne();
	        joMessage.putString("text", jsonMessage);

	        String jsonPreviousSum = "{\"previousSum\":" + socketService.getSampleTwo() + "}";
	        joMessage.putString("privousData", jsonPreviousSum);

	        // String jsonPreviousSum = "{\"keySample\":\"valueSample\"}";
	        // joMessage.putString("sample", jsonPreviousSum);
	        SockJsServer.eb.publish("app.conduit", joMessage);

		} catch (Exception e) {
			logger.error("Exception >> ", e);
		}

	}

}