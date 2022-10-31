package com.panamby.vertx.vertx_starter.eventbus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;

public class RequestResponseExample {
	
	public static void main(String[] args) {

		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(new RequestVerticle());
		vertx.deployVerticle(new ResponseVerticle());
	}

	static class RequestVerticle extends AbstractVerticle {
		
		private static final Logger LOG = LoggerFactory.getLogger(RequestVerticle.class);
		static final String ADDRESS = "my.request.address";

		@Override
		public void start(Promise<Void> startPromise) throws Exception {
			startPromise.complete();
			EventBus eventBus = vertx.eventBus();
			final String message = "Hello World!";
			LOG.debug("Sending: {}", message);
			eventBus.<String>request(ADDRESS, message, reply -> {
				LOG.debug("Response: {}", reply.result().body());
			});
		}
	}

	static class ResponseVerticle extends AbstractVerticle {
		
		private static final Logger LOG = LoggerFactory.getLogger(ResponseVerticle.class);

		@Override
		public void start(Promise<Void> startPromise) throws Exception {
			startPromise.complete();
			vertx.eventBus().<String>consumer(RequestVerticle.ADDRESS, message -> {
				LOG.debug("Received Message: {}", message.body());
				message.reply("Received your message. Thanks!");
			});
		}
		
	}
}
