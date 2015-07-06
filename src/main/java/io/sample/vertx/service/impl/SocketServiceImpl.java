package io.sample.vertx.service.impl;

import io.sample.vertx.service.SocketService;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vertx.java.core.json.JsonObject;

@Service
public class SocketServiceImpl implements SocketService {

	final Logger logger = LoggerFactory.getLogger(SocketServiceImpl.class);

	@Autowired
    private Configuration configuration;

	@Override
	public String getSampleJson(String tableName, String rowKey) throws Exception {

		JsonObject encodedJson = new JsonObject();
		
    	encodedJson.putValue("title1", "contents1");
    	encodedJson.putValue("title2", "contents2");

    	return encodedJson.toString();
	}

}
