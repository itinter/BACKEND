package com.magrabbit.internship.xpath.service.impl;

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
public class XPathServiceImpl implements XPathService {

	@Autowired
	XPathDAO xPathDao;

	@Override
	public String insertXpathDatabase(int id, XPath xpath) {
		String mess = "";
		System.out.println("insert xpath" + xpath.getElementName());
		if (this.xPathDao.save(id, xpath)) {
			mess = "Insert OK !!!";
		} else {
			mess = "Insert Failed !!!";
		}
		return mess;
	}

	public static Elements getElements(String url) throws IOException {
		Document doc = Jsoup.parse(new URL(url), 10000);
		Elements elements = doc.getAllElements();
		return elements;
	}

	public static ArrayList<Attribute> divideAttribute(Attributes attrs) {
		ArrayList<Attribute> listAttr = new ArrayList<Attribute>();
		for (Attribute i : attrs) {
			if (i.getKey().equals("id") // || i.getKey().equals("class") || i.getKey().equals("name")
					|| i.getKey().equals("href") || i.getKey().equals("src")) {
				listAttr.add(i);
			}
		}
		return listAttr;
	}

	public static ArrayList<XPath> GenXpathByAttribute(Element elm) {
		ArrayList<XPath> lstXpath = new ArrayList<XPath>();
		if (elm.attributes().asList().size() != 0) {
			for (Attribute attr : divideAttribute(elm.attributes())) {
				XPath xPath = new XPath();
				xPath.setxPath("//" + elm.tagName() + "[@" + attr + "]");
				if (elm.hasAttr("id")) {
					xPath.setElementId(elm.id());
				}
				if (elm.hasAttr("name")) {
					xPath.setElementName(elm.attr("name"));
				}
				lstXpath.add(xPath);
			}
		}
		return lstXpath;
	}

	public static ArrayList<XPath> GenXpathEleNonAttribute(Element elm) {
		ArrayList<XPath> lstxPath = new ArrayList<XPath>();
		Element parent = elm.parent();
		if (parent != null) {
			if (GenXpathByAttribute(parent) != null) {
				for (XPath xpathParent : GenXpathByAttribute(parent)) {
					XPath xpath = new XPath();
					String x = xpathParent + "/" + elm.tagName();
					xpath.setxPath(x);
					lstxPath.add(xpath);
				}
			} else {
				ArrayList<XPath> lstxPathParent = new ArrayList<XPath>();
				String xpathParent = "/" + elm.tagName();
				lstxPath = GenXpathEleNonAttribute(elm.parent());
				for (XPath x : lstxPath) {
					x.setxPath(x.getxPath() + "/" + xpathParent);
					lstxPath.add(x);
				}				
			}
		}
		return lstxPath;
	}

	@Override
	public ArrayList<XPath> getXpath(String url) {
		ArrayList<XPath> lstxPath = new ArrayList<XPath>();
		Elements e;
		try {
			e = getElements(url);
			for (Element i : e) {
				if (GenXpathByAttribute(i) != null) {
					lstxPath.addAll(GenXpathByAttribute(i));
				} else
					lstxPath.addAll(GenXpathEleNonAttribute(i));
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return lstxPath;
	}

}
