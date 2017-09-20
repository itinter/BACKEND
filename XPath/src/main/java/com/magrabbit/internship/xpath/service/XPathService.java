package com.magrabbit.internship.xpath.service;

import java.util.ArrayList;

import com.magrabbit.internship.xpath.models.XPath;

public interface XPathService {
	public ArrayList<String> getXpath(String url);
	
	String inserDatabase(XPath xpath);
}
