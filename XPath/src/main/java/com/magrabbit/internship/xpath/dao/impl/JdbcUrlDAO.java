package com.magrabbit.internship.xpath.dao.impl;

import java.util.List;

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
	@Value("${url.getoldxpath}")
	private String sqlUrlGetHtml;
	

	@Override
	public boolean save(Url url) {
		boolean rs = false;
		try {
			this.jdbcTemplate.update(this.sqlUrlSave, url.getUrl(), url.getDate(), url.getHtml());
			rs = true;
		} catch (DataAccessException e) {
			System.out.println("ERROR::: save xpath" + e.getMessage()); 
		}
		return rs;
	}

	@Override
	public int find(String url, String date) {
		java.lang.Integer id = this.jdbcTemplate.queryForObject(this.sqlUrlFind, new Object[] { url, date+"%" }, java.lang.Integer.class);
		return id;
	}
	
	@Override
	public String getOldXpath(String url, String date) {
		List<String> html = this.jdbcTemplate.queryForList(this.sqlUrlGetHtml,new Object[] { url, date }, String.class);	
		if (html.isEmpty()) {
	        return "";
	    } else {
	        return html.get(html.size()-1);
	    }
	}

}