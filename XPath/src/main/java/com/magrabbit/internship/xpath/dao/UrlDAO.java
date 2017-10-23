package com.magrabbit.internship.xpath.dao;

import com.magrabbit.internship.xpath.models.Url;

public interface UrlDAO {
	public boolean save(Url url);
	public int find(String url,String date);
	public String getOldXpath(int id);
}