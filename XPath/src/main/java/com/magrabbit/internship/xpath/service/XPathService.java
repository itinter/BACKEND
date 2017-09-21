package com.magrabbit.internship.xpath.service;

import java.util.ArrayList;

import com.magrabbit.internship.xpath.models.XPath;

public interface XPathService {
	public ArrayList<XPath> getXpath(String url);

	String insertXpathDatabase(int id,XPath xpath);
}
