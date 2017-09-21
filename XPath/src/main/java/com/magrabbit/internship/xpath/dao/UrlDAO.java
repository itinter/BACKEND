package com.magrabbit.internship.xpath.dao;

import java.sql.Timestamp;

import com.magrabbit.internship.xpath.models.Url;

public interface UrlDAO {
	public boolean save(Url url);
	public int find(String url,Timestamp date);

}