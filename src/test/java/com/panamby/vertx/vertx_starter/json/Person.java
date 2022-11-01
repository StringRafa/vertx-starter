package com.panamby.vertx.vertx_starter.json;

public class Person {

	private Integer id;
	private String name;
	private boolean lovesVertx;
	
	public Person() {
		super();
	}

	public Person(Integer id, String name, boolean lovesVertx) {
		super();
		this.id = id;
		this.name = name;
		this.lovesVertx = lovesVertx;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isLovesVertx() {
		return lovesVertx;
	}

	public void setLovesVertx(boolean lovesVertx) {
		this.lovesVertx = lovesVertx;
	}
	
}
