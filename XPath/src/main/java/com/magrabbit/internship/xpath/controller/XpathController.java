package com.magrabbit.internship.xpath.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.magrabbit.internship.xpath.models.Url;
import com.magrabbit.internship.xpath.models.XPath;
import com.magrabbit.internship.xpath.models.XPaths;
import com.magrabbit.internship.xpath.service.UrlService;
import com.magrabbit.internship.xpath.service.XPathService;
import com.magrabbit.internship.xpath.service.XPathsService;

@Controller
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
	
//	@ResponseBody
//	@RequestMapping(value = "/save", method = RequestMethod.GET)
//	public String save() {
//		Url url = new Url("http://google.com");
//		url.setDate(new Timestamp(System.currentTimeMillis()));
//		xpaths.setUrl(url);
//		System.out.println(xpaths.getUrl().toString());
//		return this.urlService.insertUrlDatabase(url);
//	}
	
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public String save(HttpServletRequest request) {
		return this.xpathsService.insertXPathsDatabase((XPaths)session.getAttribute("x"));
	}

	@ResponseBody
	@RequestMapping(value = "/getxpath", method = RequestMethod.GET)
	public XPaths getXpath(@RequestParam(value = "url") String urlget,HttpServletRequest request) {
		System.out.println(urlget);
		ArrayList<XPath> lstxpath = this.xPathService.getXpath(urlget);
		System.out.println(lstxpath);
		Url url = new Url(urlget);
		XPaths xpaths = new XPaths(url,lstxpath);
		session = request.getSession();
		session.setAttribute("x", xpaths);
		return xpaths;
	}
}
