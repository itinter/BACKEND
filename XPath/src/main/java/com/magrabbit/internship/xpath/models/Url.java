package com.magrabbit.internship.xpath.models;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Url {
	private int id;
	private String url;
	private Date date;

	public Url() {
	}

	public Url(String url) {
		super();
		this.url = url;
	}

	public Url(int id, String url, String date) {
		super();
		this.id = id;
		this.url = url;
		SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy");
		try {
			this.date = new Date(sfd.parse(date).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy");
		return sfd.format(this.date);
	}

	public void setDate(String date) {
		SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy");
		try {
			this.date = new Date(sfd.parse(date).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}