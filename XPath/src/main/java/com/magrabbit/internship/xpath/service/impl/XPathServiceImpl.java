package com.magrabbit.internship.xpath.service.impl;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
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
		String html = doc.html();
		String html2 = html.replace("<!doctype html>", "");
		Document doc2 = Jsoup.parse(html2);
		Elements elements = doc.getAllElements();
		return elements;
	}

//	public static ArrayList<Attribute> divideAttribute(Attributes attrs) {
//		ArrayList<Attribute> listAttr = new ArrayList<Attribute>();
//		for (Attribute i : attrs) {
//			if (i.getKey().equals("id") // || i.getKey().equals("class") || i.getKey().equals("name")
//					|| i.getKey().equals("href") || i.getKey().equals("src")) {
//				listAttr.add(i);
//			}
//		}
//		return listAttr;
//	}

	public static XPath GenXpathByAttributeId(Element elm) {
		
		if(!elm.id().equals("")) {
			XPath xpath = new XPath();
			xpath.setxPath("//*[@id=\"" + elm.attr("id") + "\"]");
			xpath.setElementId(elm.id());
			if (elm.hasAttr("name")) {
				xpath.setElementName(elm.attr("name"));
			}
			return xpath;
		} else return null;
//		if (elm.attributes().asList().size() != 0) {
//			for (Attribute attr : divideAttribute(elm.attributes())) {
//				XPath xPath = new XPath();
//				xPath.setxPath("//" + elm.tagName() + "[@" + attr + "]");
//				if (elm.hasAttr("id")) {
//					xPath.setElementId(elm.id());
//				}
//				if (elm.hasAttr("name")) {
//					xPath.setElementName(elm.attr("name"));
//				}
//			}
//		}
	}

	public static int checkSiblingIndexLikeTag(Element elm) {
		Element ep = elm;
		Element en = elm;
		int p=0,n=0;
			while(ep.previousElementSibling()!=null) {
				if(ep.previousElementSibling().tagName().equals(elm.tagName())) {
					p=p+1;
				}
				ep = ep.previousElementSibling();
			}
			if(p==0) {
				while(en.nextElementSibling()!=null) {
					if(en.nextElementSibling().tagName().equals(elm.tagName())) {
						n=1;break;
					}
					en=en.nextElementSibling();
				}
			}
			if(p>0) {
				return p+1;
			} else if(n==1) {
				return 1;
			} else return 0;
	}
	
	public static XPath GenXpathEleNonAttribute(Element elm) {
		XPath xpath = new XPath();
		if (elm.hasAttr("name")) {
			xpath.setElementName(elm.attr("name"));
		}
		StringBuilder path = new StringBuilder("/" + elm.nodeName());
		if(checkSiblingIndexLikeTag(elm)!=0) {
			path.append("["+checkSiblingIndexLikeTag(elm)+"]");
		}
		Elements p_elm = elm.parents();
		for(Element e : p_elm){		
			if(GenXpathByAttributeId(e) != null) {
				path.insert(0, GenXpathByAttributeId(e).getxPath());
				break;
			}
			if(checkSiblingIndexLikeTag(e)!= 0) {
				path.insert(0, "/" +e.nodeName() + "["+ checkSiblingIndexLikeTag(e) +"]");
			} else path.insert(0,"/" + e.nodeName());
		}
		xpath.setxPath(path.toString());
		
		return xpath;
	}

	@Override
	public Set<XPath> getXpath(String url) {
		Set<XPath> lstxPath = new HashSet<XPath>();
		Elements e;
		try {
			e = getElements(url);
			for (Element i : e) {
				if (GenXpathByAttributeId(i) != null ) {
					lstxPath.add(GenXpathByAttributeId(i));
				} else
					lstxPath.add(GenXpathEleNonAttribute(i));
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return lstxPath;
	}

}
