package com.magrabbit.internship.xpath.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magrabbit.internship.xpath.dao.UrlDAO;
import com.magrabbit.internship.xpath.models.Url;
import com.magrabbit.internship.xpath.service.UrlService;

@Service
public class UrlServiceImpl implements UrlService{

	@Autowired
	UrlDAO urlDao;
	
	@Override
	public String insertUrlDatabase(Url url) {
		String mess = "";
		if (this.urlDao.save(url)) {
			mess = "Saved successfully !!!";
		} else {
			mess = "Insert Failed !!!";
		}
		return mess;
	}

	@Override
	public int selectIdUrlDatabase(String url,String date) {
		// TODO Auto-generated method stub
		int id = this.urlDao.find(url, date);
		return id;
	}

	@Override
	public String getOldXpath(String url, String date) {
		return this.urlDao.getOldXpath(url,date);
	}
	
}
