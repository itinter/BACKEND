package com.magrabbit.internship.xpath.models;

import java.util.ArrayList;

public class XPaths {
	private Url url;
	private ArrayList<XPath> xpath;
	
	
	
	public XPaths() {
		super();
	}
	
	public XPaths(Url url, ArrayList<XPath> xpath) {
		super();
		this.url = url;
		this.xpath = xpath;
	}

	public Url getUrl() {
		return url;
	}
	public void setUrl(Url url) {
		this.url = url;
	}

	public ArrayList<XPath> getXpath() {
		return xpath;
	}

	public void setXpath(ArrayList<XPath> xpath) {
		this.xpath = xpath;
	}
	
}
