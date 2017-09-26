package com.magrabbit.internship.xpath.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.magrabbit.internship.xpath.dao.XPathDAO;
import com.magrabbit.internship.xpath.models.XPath;

@Service
public class XPathDAOImpl implements XPathDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Value("${xpath.save}")
	private String sqlXpathSave;

	@Override
	public boolean save(int id,XPath xpath) {
		// TODO Auto-generated method stub
		this.jdbcTemplate.update(this.sqlXpathSave,id,xpath.getxPath(),xpath.getUiDisplay(),xpath.getElementId(),xpath.getElementName());
		return true;
	}

}
