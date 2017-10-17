package com.magrabbit.internship.xpath.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
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
	public void save(final int id,final List<XPath> xpaths) {
		// TODO Auto-generated method stub
		this.jdbcTemplate.batchUpdate(this.sqlXpathSave,new BatchPreparedStatementSetter() {
			@Override
	          public void setValues(PreparedStatement ps, int i) throws SQLException {
	              XPath xpath = xpaths.get(i);
	              ps.setInt(1, id);
	              ps.setString(2, xpath.getxPath());
	              ps.setString(3, xpath.getUiDisplay());
	              ps.setString(4, xpath.getElementId());
	              ps.setString(5, xpath.getElementName());
	            }

	            public int getBatchSize() {
	              return xpaths.size();
	            }
	          });
	}

}
