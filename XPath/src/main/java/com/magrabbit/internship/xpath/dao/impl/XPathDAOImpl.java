package com.magrabbit.internship.xpath.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.magrabbit.internship.xpath.dao.XPathDAO;
import com.magrabbit.internship.xpath.models.XPath;

@Service
public class XPathDAOImpl implements XPathDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void save(XPath xpath) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean testInsertDatabase(XPath xpath) {
		// TODO viết code insert data into database ở đây
		// this.jdbcTemplate.update("Câu query insert",
		// xpath.getElementName(),
		// xpath.getxPath()
		// );
		return true;
	}

}
