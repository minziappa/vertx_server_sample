package io.sample.vertx.service.impl;

import io.sample.vertx.dao.HBaseDAO;
import io.sample.vertx.service.SocketService;

import org.apache.commons.configuration.Configuration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Result;
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
	@Autowired
	private HBaseDAO hBaseDAO;

	@Override
	public String getSampleJson(String tableName, String rowKey) throws Exception {

		Result r = hBaseDAO.result(tableName, rowKey);
		JsonObject encodedJson = new JsonObject();

		String value = "";
    	for (KeyValue kv : r.raw()) {
    		value = new String(kv.getValue());
    		encodedJson.putValue(rowKey, value);
    		logger.info("rowKey = " + rowKey + ", rowValue = " + value);
    	}
    	encodedJson.putValue("title", "contents");

    	return encodedJson.toString();
	}

}
