package com.panamby.vertx.vertx_starter;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class MainVerticle extends AbstractVerticle {
	
	private static final Logger LOG = LoggerFactory.getLogger(MainVerticle.class);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    vertx.createHttpServer().requestHandler(req -> {
      req.response()
        .putHeader("content-type", "text/plain")
        .end("Hello from Vert.x!");
    }).listen(8888, http -> {
      if (http.succeeded()) {
        startPromise.complete();
        System.out.println("HTTP server started on port 8888");
      } else {
        startPromise.fail(http.cause());
      }
      vertx.setPeriodic(100, id -> LOG.info("" + new Random().nextDouble()));
    });
  }
  
//  public static void main(String[] args) {
//	  var vertx = Vertx.vertx();
//	  vertx.deployVerticle(new MainVerticle());
//  }
}
