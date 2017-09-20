package com.magrabbit.internship.xpath.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magrabbit.internship.xpath.dao.XPathDAO;
import com.magrabbit.internship.xpath.models.XPath;
import com.magrabbit.internship.xpath.service.XPathService;

@Service
public class XPathServiceImpl implements XPathService{
	
	@Autowired
    XPathDAO xPathDao;

	

	@Override
	public String inserDatabase(XPath xpath) {
		String mess = "";
		System.out.println("insert xpath" + xpath.getElementName());
		if(this.xPathDao.testInsertDatabase(xpath)) { 
			mess = "Insert OK !!!";
		}else {
			mess = "Insert Failed !!!";
		}
		return mess;
	}
	
//	public static String getFileHtml(String url) throws IOException {
//		Document doc = Jsoup.connect(url).get();  
//	       String html = doc.outerHtml(); 
//	       File filehtml = new File("\\src\\main\\resources\\file\\html.html");
//	       FileWriter fw = new FileWriter(filehtml);
//	       BufferedWriter bw = new BufferedWriter(fw);
//	       bw.write(html);
//	       bw.close();
//	       fw.close();
//		return html;
//	}
	
	public static Elements getElements(String url) throws IOException { 
		Document doc = Jsoup.parse(new URL(url),10000);
		//doc.getAllElements();
		Elements elements = doc.getAllElements();
		return elements;
	}
	
	public static Attributes getAttributeOfElement(Element elm) {		
			if(elm.attributes().asList().size() != 0) {
				return elm.attributes();
			}else return null;
			
	}
	public static String getElementName(Element elm){
		int space = elm.toString().indexOf(" ");
		int brace = elm.toString().indexOf(">");
		int endIndex = 0;
		if(space < brace && space > -1) endIndex = space;
		else endIndex = brace;
		String elmname = elm.toString().substring(1, endIndex);
		return elmname;
	}
	
	
	public static String isNormal(String str) {
		str=str.trim();
		while(str.indexOf("  ")>0) {
			str=str.replace("  ", " ");
		}
		return str;
	}
	public static ArrayList<Attribute> divideAttribute(Attributes attrs) {
		ArrayList<Attribute> listAttr = new ArrayList<Attribute>();
		for(Attribute i:attrs) {
			if(i.getKey().equals("id") || i.getKey().equals("class") || i.getKey().equals("name") || i.getKey().equals("href") || i.getKey().equals("src")) {
				listAttr.add(i);
			}
		}
		//String strAttrs = isNormal(attrs.toString());
		//String[] listAttr = strAttrs.split(" ");
		return listAttr;
	}
	
	public static ArrayList<XPath> GenXpathByAttribute(Element elm){
		ArrayList<XPath> lstXpath = new ArrayList<XPath>();
		if(elm.attributes().asList().size() != 0) {
			for(Attribute attr: divideAttribute(elm.attributes())) {
				XPath x = new XPath();
				x.setxPath("//"+getElementName(elm)+"[@"+ attr +"]");
				lstXpath.add(x);
			}
		}return lstXpath;
	}
	
	@Override
	public ArrayList<XPath> getXpath(String url){
		ArrayList<XPath> xPath = new ArrayList<XPath>();
		Elements e;
		try {
			e = getElements(url);
			for(Element i:e) {
				if(GenXpathByAttribute(i) != null) {
					xPath.addAll(GenXpathByAttribute(i));
				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return xPath;
	}
 
}
