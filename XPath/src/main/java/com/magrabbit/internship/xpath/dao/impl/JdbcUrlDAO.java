package com.magrabbit.internship.xpath.dao.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.magrabbit.internship.xpath.dao.UrlDAO;
import com.magrabbit.internship.xpath.models.Url;

@Service
public class JdbcUrlDAO implements UrlDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public boolean save(Url url) {
		this.jdbcTemplate.update("${url.save}",url.getUrl(),url.getDate());
		return true;
	}

	@Override
	public int find(String url, Timestamp date) {
		int id = 0;
		id = (int)this.jdbcTemplate.queryForObject(
				"${url.findid}", new Object[] { url,date }, int.class);

		return id;
	}

}