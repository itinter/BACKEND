package com.magrabbit.internship.xpath.service;

import java.sql.Timestamp;

import com.magrabbit.internship.xpath.models.Url;

public interface UrlService {
	String insertUrlDatabase(Url url);
	public int selectIdUrlDatabase(String url,Timestamp date);
}
