package io.sample.vertx.server;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.core.sockjs.SockJSServer;
import org.vertx.java.core.Handler;
import org.vertx.java.platform.PlatformLocator;
import org.vertx.java.platform.impl.PlatformManagerInternal;

public class OneSockJsServer {

	final Logger logger = LoggerFactory.getLogger(OneSockJsServer.class);

	// private Vertx vertx = null;
	protected static PlatformManagerInternal platformManager = (PlatformManagerInternal)PlatformLocator.factory.createPlatformManager();
	protected Vertx vertx = platformManager.vertx();

	public void init() {
		
		System.out.println("init - start");
		// vertx = VertxFactory.newVertx();

//		PlatformLocator pl = new PlatformLocator();
//		PlatformManagerFactory pmf =pl.factory;
//		PlatformManager pm = pmf.createPlatformManager();

        HttpServer server = vertx.createHttpServer();
        JsonObject config = new JsonObject().putString("prefix", "/chat");
        // Set security permission to let everything go through
        JsonArray permitted = new JsonArray()
        	.add(new JsonObject().putString("address", "chat.msg"))
        	.add(new JsonObject().putString("address", "chat.manager.ping"))
        	.add(new JsonObject().putString("address", "chat.manager.list"));

        // Create SockJS and bridge it to the Event Bus
        SockJSServer sockJSServer = vertx.createSockJSServer(server);
        sockJSServer.bridge(config, permitted, permitted);

        // Start the server
        server.listen(8091, "172.28.153.116");

        ModuleDeployment[] deployments = new ModuleDeployment[] {
                // webServerDeployment(),
                // new ModuleDeployment("chat-manager", new JsonObject()),
                // new ModuleDeployment("wolfram-alpha", new JsonObject()),
                // new ModuleDeployment("stats-jvm", new JsonObject()),
                // new ModuleDeployment("hal", new JsonObject())
            };

        deployModules(Arrays.asList(deployments), new Handler<AsyncResult<String>>() {
			@Override
			public void handle(AsyncResult<String> event) {
				// TODO Auto-generated method stub
				logger.debug("Finished deploying modules");
			}
        });

        System.out.println("init - end");
	}

	public void destory() {
		System.out.println("init - start");
		vertx.stop();
		System.out.println("destory - end");
	}

    private void deployModules(final List<ModuleDeployment> moduleDeployments, final Handler<AsyncResult<String>> doneHandler) {
    	java.net.URL[] classpath =  {};
    	final Iterator<ModuleDeployment> it = moduleDeployments.iterator();
    	if(it.hasNext()) {
    		ModuleDeployment deployment = it.next();
    		 platformManager.deployVerticle(deployment.moduleName, deployment.moduleConfig, classpath, 1, "", doneHandler);
    	}

//        final Handler<Void> handler = new Handler<Void>() {
//            
//            public void handle(Void event) {
//                if(it.hasNext()) {
//                	
//                    ModuleDeployment deployment = it.next();
//                    logger.debug("Deploying module: " + deployment.moduleName);
//                    // platformManager.deployVerticle(deployment.moduleName, deployment.moduleConfig, 1, this);
//                    
//                    platformManager.deployVerticle(deployment.moduleName, deployment.moduleConfig, classpath, 1, "",
//                            new Handler<String>() {
//                        public void handle(final String s) {
//                            System.out.println("Verticle Deployed");
//                        }
//                  });
//                } else {
//                    doneHandler.handle(null);
//                }
//            }
//        };

//        handler.handle(null);
    }

//    private ModuleDeployment webServerDeployment() {
//        logger.debug("Creating web server");
//        JsonObject config = new JsonObject();
//        config.putString("web_root", "web");
//        config.putString("index_page", "index.html");
//        config.putString("host", "172.28.153.116");
//        config.putNumber("port", 8092);
//        return new ModuleDeployment("web-server", config);
//    }

    private class ModuleDeployment {
        public String moduleName;
        public JsonObject moduleConfig;

        private ModuleDeployment(String moduleName, JsonObject moduleConfig) {
            this.moduleName = moduleName;
            this.moduleConfig = moduleConfig;
        }
    }

}