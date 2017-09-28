package com.magrabbit.internship.xpath.models;

import java.util.Set;

public class XPaths {
	private Url url;
	private Set<XPath> xpath;
	
	
	
	public XPaths() {
		super();
	}
	
	public XPaths(Url url, Set<XPath> xpath) {
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

	public Set<XPath> getXpath() {
		return xpath;
	}

	public void setXpath(Set<XPath> xpath) {
		this.xpath = xpath;
	}
	
}
