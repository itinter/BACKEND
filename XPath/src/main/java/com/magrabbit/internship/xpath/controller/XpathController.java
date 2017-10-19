package com.magrabbit.internship.xpath.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

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
	@ResponseStatus(value=HttpStatus.OK)
	public String getxpath4(@RequestBody String urlInput, HttpServletRequest request) {
		System.out.println(urlInput);
		String html = this.xPathService.getXpath2(urlInput);
		Url url = new Url(urlInput);
		url.setHtml(html);
		List<XPath> lstxpath = this.xPathService.getXpath(urlInput);
		XPaths xpaths = new XPaths(url, lstxpath);
		session = request.getSession();
		session.setAttribute("x", xpaths);
		System.out.print(html);
		return html;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public String save(HttpServletRequest request) {
		System.out.print("save");
		String rs = this.xpathsService.insertXPathsDatabase((XPaths) session.getAttribute("x"));
		session.invalidate();
		return rs;
	}

	// @RequestMapping(value = "/getxpath/{url:.*}")
	// @RequestMapping(value = "/getxpath/{url:regular_expression}")
	// public XPaths getXpath(@PathVariable("url") String urlget,HttpServletRequest
	// request) {
	// System.out.println(urlget);
	// Set<XPath> lstxpath = this.xPathService.getXpath(urlget);
	// Url url = new Url(urlget);
	// XPaths xpaths = new XPaths(url,lstxpath);
	// session = request.getSession();
	// session.setAttribute("x", xpaths);
	// return xpaths;
	// }

//	@RequestMapping(value = "/getxpath/{urlBase}/**", method = RequestMethod.GET)
//	public XPaths getXpath(@PathVariable String urlBase, HttpServletRequest request) {
//		final String path = request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE).toString();
//		final String bestMatchingPattern = request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE)
//				.toString();
//
//		String arguments = new AntPathMatcher().extractPathWithinPattern(bestMatchingPattern, path);
//
//		String urlget;
//		if (null != arguments && !arguments.isEmpty()) {
//			urlget = urlBase + '/' + arguments;
//		} else {
//			urlget = urlBase;
//		}
//		urlget = "http://" + urlget;
//		urlget = urlget.substring(0, urlget.length() - 1);
//		Set<XPath> lstxpath = this.xPathService.getXpath(urlget);
//		Url url = new Url(urlget);
//		url.setHtml(this.xPathService.getHtml(urlget));
//		System.out.println(this.xPathService.getXpath2(urlget));
//		XPaths xpaths = new XPaths(url, lstxpath);
//		session = request.getSession();
//		session.setAttribute("x", xpaths);
//		return xpaths;
//	}
//
//	@RequestMapping(value = "/getxpath2/{urlBase}/**", method = RequestMethod.GET)
//	@ResponseBody
//	public String getXpath2(@PathVariable String urlBase, HttpServletRequest request) {
//		final String path = request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE).toString();
//		final String bestMatchingPattern = request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE)
//				.toString();
//
//		String arguments = new AntPathMatcher().extractPathWithinPattern(bestMatchingPattern, path);
//
//		String urlget;
//		if (null != arguments && !arguments.isEmpty()) {
//			urlget = urlBase + '/' + arguments;
//		} else {
//			urlget = urlBase;
//		}
//		urlget = "http://" + urlget;
//		urlget = urlget.substring(0, urlget.length() - 1);
//		//Set<XPath> lstxpath = this.xPathService.getXpath(urlget);
//		//Url url = new Url(urlget);
//		//url.setHtml(this.xPathService.getHtml(urlget));
//		//XPaths xpaths = new XPaths(url, lstxpath);
//		//session = request.getSession();
//		//session.setAttribute("x", xpaths);
//		return this.xPathService.getXpath2(urlget);
//	} 
//	
//	@RequestMapping(value = "/getxpath3", method = RequestMethod.GET) 
//	@ResponseStatus(value=HttpStatus.OK)
//	public String getxpath3(@RequestParam("url") String url) {
//		String html = this.xPathService.getXpath2(url);
//		return html;
//	} 
	 
}
