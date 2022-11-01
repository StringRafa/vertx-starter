package com.panamby.vertx.vertx_starter.eventbus.customcodec;

public class Pong {

	private Integer id;

	public Pong() {
		
	}
	
	public Pong(Integer id) {
		super();
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Pong [id=" + id + "]";
	}
	
}
