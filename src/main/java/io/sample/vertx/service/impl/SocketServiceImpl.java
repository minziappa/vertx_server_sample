package io.sample.vertx.service.impl;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

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
	public String getSampleOne() throws Exception {

		JsonObject encodedJson = new JsonObject();

		int longChargeTotalSum = 0;

		Map<String, Integer> mapChargeSum = new TreeMap<String, Integer>();
		DecimalFormat dfHour = new DecimalFormat("00");

		Random rand = new Random();
		int randomNum1 = rand.nextInt((20 - 1) + 1) + 1;
		mapChargeSum.put("nowSum", randomNum1);
		mapChargeSum.put("h" + dfHour.format(0), rand.nextInt((10 - 1) + 1) + 1);
		mapChargeSum.put("h" + dfHour.format(1), rand.nextInt((10 - 1) + 1) + 1);

		Set<Entry<String, Integer>> set = mapChargeSum.entrySet();
		Iterator<Entry<String, Integer>> it = set.iterator();

		while(it.hasNext()) {
			Map.Entry<String, Integer> e = (Map.Entry<String, Integer>)it.next();
			encodedJson.putValue(e.getKey(), e.getValue());
			longChargeTotalSum = longChargeTotalSum + e.getValue();
		}
		encodedJson.putValue("totalSum", longChargeTotalSum);


		int randomNum2 = rand.nextInt((20 - 1) + 1) + 1;
    	encodedJson.putValue("totalSum", randomNum2);

    	return encodedJson.toString();
	}

	@Override
	public String getSampleTwo() throws Exception {

		Random rand = new Random();
		int randomNum = rand.nextInt((20 - 1) + 1) + 1;

		return String.valueOf(randomNum);
	}

}
