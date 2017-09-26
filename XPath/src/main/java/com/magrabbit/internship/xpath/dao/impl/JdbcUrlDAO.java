package com.magrabbit.internship.xpath.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.magrabbit.internship.xpath.dao.UrlDAO;
import com.magrabbit.internship.xpath.models.Url;

@Service
public class JdbcUrlDAO implements UrlDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Value("${url.save}")
	private String sqlUrlSave;
	@Value("${url.findid}")
	private String sqlUrlFind;

	@Override
	public boolean save(Url url) {
		boolean rs = false;
		try {
			this.jdbcTemplate.update(this.sqlUrlSave, url.getUrl(), url.getDate());
			rs = true;
		} catch (DataAccessException e) {
			System.out.println("ERROR::: save xpath" + e.getMessage()); 
		}
		return rs;
	}

	@Override
	public int find(String url, String date) {
		java.lang.Integer id = this.jdbcTemplate.queryForObject(this.sqlUrlFind, new Object[] { url, date }, java.lang.Integer.class);
		return id;
	}

}