package com.magrabbit.internship.xpath.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magrabbit.internship.xpath.dao.UrlDAO;
import com.magrabbit.internship.xpath.dao.XPathDAO;
import com.magrabbit.internship.xpath.models.XPath;
import com.magrabbit.internship.xpath.models.XPaths;
import com.magrabbit.internship.xpath.service.XPathsService;

@Service
public class XPathsServiceImpl implements XPathsService{

	@Autowired
	XPathDAO xPathDao;
	@Autowired
	UrlDAO urlDao;
	
	
	@Override
	public String insertXPathsDatabase(XPaths xpaths) {
		String mess = "";
		if (this.urlDao.save(xpaths.getUrl())) {
				if(this.urlDao.find(xpaths.getUrl().getUrl(), xpaths.getUrl().getDate())!=0) {
					for(XPath xpath:xpaths.getXpath()) {
						if(this.xPathDao.save(this.urlDao.find(xpaths.getUrl().getUrl(), xpaths.getUrl().getDate()),xpath)) {
							mess = "Insert OK !!!";
						}else mess = "Insert Failed !!!";
					}
				} else mess = "Insert Failed !!!";
			} else {
			mess = "Insert Failed !!!";
		}
		return mess;
	}

}
