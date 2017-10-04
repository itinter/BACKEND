package com.magrabbit.internship.xpath.service;

import com.magrabbit.internship.xpath.models.Url;

public interface UrlService {
	String insertUrlDatabase(Url url);
	int selectIdUrlDatabase(String url,String date);
}
