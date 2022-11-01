package com.panamby.vertx.vertx_starter.eventbus.customcodec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;

public class PingPongExample {
	
	private static final Logger LOG = LoggerFactory.getLogger(PingPongExample.class);
	
	public static void main(String[] args) {

		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(new PingVerticle(), longOnError());
		vertx.deployVerticle(new PongVerticle(), longOnError());
	}

	private static Handler<AsyncResult<String>> longOnError() {
		return ar -> {
			if (ar.failed()) {
				LOG.error("err", ar.cause());				
			}
		};
	}

	static class PingVerticle extends AbstractVerticle {
		
		private static final Logger LOG = LoggerFactory.getLogger(PingVerticle.class);
		static final String ADDRESS = PingVerticle.class.getName();

		@Override
		public void start(Promise<Void> startPromise) throws Exception {
			EventBus eventBus = vertx.eventBus();
			final Ping message = new Ping("Hello", true);
			LOG.debug("Sending: {}", message);
			// Register only once
			eventBus.registerDefaultCodec(Ping.class, new LocalMessageCodec<>(Ping.class));
			eventBus.<Pong>request(ADDRESS, message, reply -> {
				if (reply.failed()) {
					LOG.error("Failed: {}", reply.cause());
					return;
				}
				LOG.debug("Response: {}", reply.result().body());
			});
			startPromise.complete();
		}
	}

	static class PongVerticle extends AbstractVerticle {
		
		private static final Logger LOG = LoggerFactory.getLogger(PongVerticle.class);

		@Override
		public void start(Promise<Void> startPromise) throws Exception {
			// Register only once
			vertx.eventBus().registerDefaultCodec(Pong.class, new LocalMessageCodec<>(Pong.class));
			vertx.eventBus().<Ping>consumer(PingVerticle.ADDRESS, message -> {
				LOG.debug("Received Message: {}", message.body());
				message.reply(new Pong(0));
			}).exceptionHandler(error -> {
				LOG.error("Error: ", error);
			});
			startPromise.complete();
		}
		
	}
}
