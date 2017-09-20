package com.magrabbit.internship.xpath.models;

public class XPath {
	private String xPath, uiDisplay, elementId, elementName;

	public XPath() {

	}

	public XPath(int urlId, String xPath, String uiDisplay, String elementId, String elementName) {
		super();
		this.xPath = xPath;
		this.uiDisplay = uiDisplay;
		this.elementId = elementId;
		this.elementName = elementName;
	}

	public String getxPath() {
		return xPath;
	}

	public void setxPath(String xPath) {
		this.xPath = xPath;
	}

	public String getUiDisplay() {
		return uiDisplay;
	}

	public void setUiDisplay(String uiDisplay) {
		this.uiDisplay = uiDisplay;
	}

	public String getElementId() {
		return elementId;
	}

	public void setElementId(String elementId) {
		this.elementId = elementId;
	}

	public String getElementName() {
		return elementName;
	}

	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

}