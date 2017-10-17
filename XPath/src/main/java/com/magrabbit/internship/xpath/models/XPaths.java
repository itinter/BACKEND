package com.magrabbit.internship.xpath.models;

import java.util.List;

public class XPaths {
	private Url url;
	private List<XPath> xpath;
	
	
	
	public XPaths() {
		super();
	}
	
	public XPaths(Url url, List<XPath> xpath) {
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

	public List<XPath> getXpath() {
		return xpath;
	}

	public void setXpath(List<XPath> xpath) {
		this.xpath = xpath;
	}
	
}
