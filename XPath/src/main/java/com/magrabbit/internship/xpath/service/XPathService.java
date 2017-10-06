package com.magrabbit.internship.xpath.service;

import java.util.Set;

import com.magrabbit.internship.xpath.models.XPath;

public interface XPathService {
	Set<XPath> getXpath(String url);

	String insertXpathDatabase(int id,XPath xpath);
	
	String getHtml(String url);
}
