package com.magrabbit.internship.xpath.service;

import com.magrabbit.internship.xpath.models.XPaths;

public interface XPathsService {
	String insertXPathsDatabase(XPaths xpaths);
	String getOldXpath();
}
