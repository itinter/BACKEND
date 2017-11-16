package com.magrabbit.internship.xpath.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

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
import com.magrabbit.internship.xpath.models.XPath;
import com.magrabbit.internship.xpath.models.XPaths;
import com.magrabbit.internship.xpath.service.UrlService;
import com.magrabbit.internship.xpath.service.XPathService;
import com.magrabbit.internship.xpath.service.XPathsService;

@RestController
public class XpathController {

	@Autowired
	XPathService xPathService;

	@Autowired
	UrlService urlService;

	@Autowired
	XPathsService xpathsService;
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
		List<XPath> lstxpath = this.xPathService.getXpath(urlInput);
		XPaths xpaths = new XPaths(url, lstxpath);
		session = request.getSession();
		session.setAttribute("x", xpaths);
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
		return this.xpathsService.getOldXpath(url,strDate);
		//return "";
	}
	
	@RequestMapping(value = "/getxpath5", method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseStatus(value = HttpStatus.OK)
	public String getxpath5(@RequestParam("url") String url,@RequestParam("content") String content, HttpServletRequest request) {
		System.out.print("ok");
		System.out.print(url);
		String html = this.xPathService.getXpath3(url,content);
		return html;
	}

	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public String save(HttpServletRequest request) {
		String rs = "Save Failed !";
		if(session.getAttribute("x")!=null) {
			rs = this.xpathsService.insertXPathsDatabase((XPaths) session.getAttribute("x"));
			session.invalidate();
		}
		return rs;
	}
}
