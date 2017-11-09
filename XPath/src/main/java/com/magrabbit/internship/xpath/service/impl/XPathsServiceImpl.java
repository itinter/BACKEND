package com.magrabbit.internship.xpath.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magrabbit.internship.xpath.dao.UrlDAO;
import com.magrabbit.internship.xpath.dao.XPathDAO;
import com.magrabbit.internship.xpath.models.XPath;
import com.magrabbit.internship.xpath.models.XPaths;
import com.magrabbit.internship.xpath.service.XPathsService;

@Service
public class XPathsServiceImpl implements XPathsService {

	@Autowired
	XPathDAO xPathDao;
	@Autowired
	UrlDAO urlDao;

	@Override
	public String insertXPathsDatabase(XPaths xpaths) {
		String result = "The system has encountered an error. Please try again later.";
		if (this.urlDao.save(xpaths.getUrl())) {
			if (this.urlDao.find(xpaths.getUrl().getUrl(), xpaths.getUrl().getDate()) != 0) {
				this.xPathDao.save(this.urlDao.find(xpaths.getUrl().getUrl(), xpaths.getUrl().getDate()),xpaths.getXpath());
				result = "Saved successfully";
				}
		} 
		return result;
	}
	
	@Override
	public String getOldXpath(String url, String date) {
		return this.urlDao.getOldXpath(url,date);
	}

}
