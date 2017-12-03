package com.magrabbit.internship.xpath.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.magrabbit.internship.xpath.models.Url;
import com.magrabbit.internship.xpath.service.UrlService;
import com.magrabbit.internship.xpath.service.XPathService;

@RestController
public class XpathController {

	@Autowired
	XPathService xPathService;

	@Autowired
	UrlService urlService;

	static HttpSession session;

	@RequestMapping(value = { "/" })
	public String showHomeScreen() {
		System.out.println("Home Xpath");
		return "index";
	}

	@RequestMapping(value = "/getxpath4", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public String getxpath4(@RequestBody String urlInput, HttpServletRequest request) {
		String html = this.xPathService.getXpath2(urlInput);
		Url url = new Url(urlInput);
		url.setHtml(html);
		session = request.getSession();
		session.setAttribute("x", url);
		return html;
	}
	
	@RequestMapping(value = "/getoldxpath", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public String getoldxpath(@RequestParam("url") String url,@RequestParam("date") String date,HttpServletRequest request) {
		String strDate = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();    
	         cal.setTime(new SimpleDateFormat("EEE MMM dd yyyy").parse(date));    
	         cal.add( Calendar.DATE, 1 );      
	         strDate = sdf.format(cal.getTime());
		} catch (ParseException e) {
			return "";
		}
		System.out.print(strDate);
		return this.urlService.getOldXpath(url,strDate);
		//return "";
	}
	
	@RequestMapping(value = "/getxpath5", method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseStatus(value = HttpStatus.OK)
	public String getxpath5(@RequestParam("url") String urlInput,@RequestParam("content") String content, HttpServletRequest request) {
		String html = this.xPathService.getXpath3(urlInput,content);
		Url url = new Url(urlInput);
		url.setHtml(html);
		session = request.getSession();
		session.setAttribute("x", url);
		return html;
	}

	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public String save(HttpServletRequest request) {
		String rs = "Save Failed !";
		if(session.getAttribute("x")!=null) {
			System.out.println((Url)session.getAttribute("x"));
			rs = this.urlService.insertUrlDatabase((Url)session.getAttribute("x"));
			session.invalidate();
		}
		return rs;
	}
}
