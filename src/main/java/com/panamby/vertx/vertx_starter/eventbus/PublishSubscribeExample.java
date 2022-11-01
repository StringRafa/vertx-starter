 package com.panamby.vertx.vertx_starter.eventbus;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

public class PublishSubscribeExample {

	public static void main(String[] args) {

		final Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(new Publish());
		vertx.deployVerticle(new Subscriber1());
		vertx.deployVerticle(Subscriber2.class.getName(), new DeploymentOptions().setInstances(2));
	}
	
	public static class Publish extends AbstractVerticle {
		
		@Override
		public void start(Promise<Void> startPromise) throws Exception {
			startPromise.complete();
			vertx.setPeriodic(Duration.ofSeconds(10).toMillis(), id -> 
				vertx.eventBus().publish(Publish.class.getName(), "A message for everyone!")		
			);
		}		
	}
	
	public static class Subscriber1 extends AbstractVerticle {
		
		private static final Logger LOG = LoggerFactory.getLogger(Subscriber1.class);
		
		@Override
		public void start(Promise<Void> startPromise) throws Exception {
			startPromise.complete();
			vertx.eventBus().<String>consumer(Publish.class.getName(), message -> {
				LOG.debug("Subscriber 1 Received: {}", message.body());
			});
		}	
	}
	
	public static class Subscriber2 extends AbstractVerticle {
		
		private static final Logger LOG = LoggerFactory.getLogger(Subscriber1.class);
		
		@Override
		public void start(Promise<Void> startPromise) throws Exception {
			startPromise.complete();
			vertx.eventBus().<String>consumer(Publish.class.getName(), message -> {
				LOG.debug("Subscriber 2 Received: {}", message.body());
			});
		}	
	}
}
