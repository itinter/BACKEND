package com.magrabbit.internship.xpath.models;


import java.sql.Timestamp;

public class Url {
	private int id;
	private String url;
	private Timestamp date;

	public Url() {
	}

	public Url(String url) {
		super();
		this.url = url;
		this.date = new Timestamp(System.currentTimeMillis());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDate() {
		return date.toString();
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}
	
	
}