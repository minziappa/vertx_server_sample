package io.sample.vertx.common;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTablePool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class HBaseConfig {

	final Logger logger = LoggerFactory.getLogger(HBaseConfig.class);

	@Autowired
    private org.apache.commons.configuration.Configuration configuration;

	@Bean
	public Configuration defaultHBaseConfig() throws IOException{
		Configuration conf = null;
		try{
			conf = HBaseConfiguration.create();
			conf.set("hbase.master", configuration.getString("hbase.master"));
			conf.set("hbase.zookeeper.quorum", configuration.getString("hbase.zookeeper.quorum"));
			conf.set("hbase.zookeeper.property.clientPort", configuration.getString("hbase.zookeeper.property.clientPort"));
		}catch(Exception ex){
			logger.error("Exception", ex);
		}

		return conf;
	}

	@Bean
	public HTablePool defaultHTablePool() throws IOException {
		HTablePool tablePool = new HTablePool(defaultHBaseConfig(), 30);
		return tablePool;
	}

}