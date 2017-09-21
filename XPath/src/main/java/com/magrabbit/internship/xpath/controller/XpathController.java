package com.magrabbit.internship.xpath.controller;

import java.util.ArrayList;

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
	UrlService urlService;
	XPathsService xpathsService;
	static XPaths xpaths;

	@RequestMapping(value = { "/" })
	public String showHomeScreen() {
		System.out.println("Home Xpath");
		return "index";
	}

	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public String save() {
		return this.urlService.insertUrlDatabase(xpaths.getUrl());
	}
	
//	@ResponseBody
//	@RequestMapping(value = "/save", method = RequestMethod.GET)
//	public String save() {
//		return this.xpathsService.insertXPathsDatabase(this.xpaths);
//	}

	@ResponseBody
	@RequestMapping(value = "/getxpath", method = RequestMethod.GET)
	public XPaths getXpath(@RequestParam(value = "url") String urlget) {
		System.out.println(urlget);
		ArrayList<XPath> lstxpath = this.xPathService.getXpath(urlget);
		System.out.println(lstxpath);
		Url url = new Url(urlget);
		this.xpaths = new XPaths(url,lstxpath);
		return this.xpaths;
	}
}
