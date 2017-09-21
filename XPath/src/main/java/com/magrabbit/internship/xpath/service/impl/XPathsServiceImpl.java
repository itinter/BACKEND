package com.magrabbit.internship.xpath.service.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
	UrlDAO urlDao;
	
	
	@Override
	public String insertXPathsDatabase(XPaths xpaths) {
		String mess = "";
		if (this.urlDao.save(xpaths.getUrl())) {
			SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			Timestamp time;
			try {
				time = (Timestamp) sf.parse(xpaths.getUrl().getDate());
				if(this.urlDao.find(xpaths.getUrl().getUrl(), time)!=0) {
					for(XPath xpath:xpaths.getXpath()) {
						if(this.xPathDao.save(this.urlDao.find(xpaths.getUrl().getUrl(), time),xpath)) {
							mess = "Insert OK !!!";
						}else mess = "Insert Failed !!!";
					}
				} else mess = "Insert Failed !!!";
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			mess = "Insert Failed !!!";
		}
		return mess;
	}

}
