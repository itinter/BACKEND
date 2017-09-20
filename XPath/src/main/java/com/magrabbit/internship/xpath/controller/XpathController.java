package com.magrabbit.internship.xpath.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.magrabbit.internship.xpath.models.XPath;
import com.magrabbit.internship.xpath.service.XPathService;

@Controller
public class XpathController {
	
	@Autowired
	XPathService xPathService;

	@RequestMapping(value = { "/" })
	public String showHomeScreen()   {
		System.out.println("Home Xpath");
		return "index";
	}
	
	@ResponseBody
	@RequestMapping(value = "/insert", method = RequestMethod.GET)
	public String insertUser(@RequestParam(value = "xPath") String xPath){ 
		System.out.println(xPath);
		XPath xpath = new XPath();
		xpath.setElementName(xPath);
		return this.xPathService.inserDatabase(xpath);
	}
}