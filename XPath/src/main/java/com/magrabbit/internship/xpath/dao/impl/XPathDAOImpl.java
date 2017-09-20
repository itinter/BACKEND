package com.magrabbit.internship.xpath.dao.impl;

import org.springframework.stereotype.Service;

import com.magrabbit.internship.xpath.dao.XPathDAO;
import com.magrabbit.internship.xpath.models.XPath;

@Service
public class XPathDAOImpl implements XPathDAO {

	@Override
	public void save(XPath xpath) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean testInsertDatabase(XPath xpath) {
		// TODO viết code insert data into database ở đây
		return true;
	}

}
