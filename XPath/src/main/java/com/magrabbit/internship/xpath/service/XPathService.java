package com.magrabbit.internship.xpath.service;

import java.util.List;

import com.magrabbit.internship.xpath.models.XPath;

public interface XPathService {
	List<XPath> getXpath(String url);

	//String insertXpathDatabase(int id,XPath xpath);
	
	String getHtml(String url);
	String getXpath2(String url);
}
