package com.panamby.vertx.vertx_starter.eventbus.customcodec;

public class Ping {

	private String message;
	private boolean enabled;
	
	public Ping() {
		super();
	}

	public Ping(String message, boolean enabled) {
		super();
		this.message = message;
		this.enabled = enabled;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "Ping [message=" + message + ", enabled=" + enabled + "]";
	}
	
}
