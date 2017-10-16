package com.magrabbit.internship.xpath.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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

	public static Elements getElements(String html) {
		Document doc = Jsoup.parse(html);
		Elements elements = doc.getAllElements();
		return elements;
	}

	public static XPath GenXpathByAttributeId(Element elm) {

		if (!elm.id().equals("")) {
			XPath xpath = new XPath();
			xpath.setxPath("//*[@id='" + elm.attr("id") + "']");
			xpath.setElementId(elm.id());
			if (elm.hasAttr("name")) {
				xpath.setElementName(elm.attr("name"));
			}
			return xpath;
		} else
			return null;
	}

	public static int checkSiblingIndexLikeTag(Element elm) {
		Element ep = elm;
		Element en = elm;
		int p = 0, n = 0;
		while (ep.previousElementSibling() != null) {
			if (ep.previousElementSibling().tagName().equals(elm.tagName())) {
				p = p + 1;
			}
			ep = ep.previousElementSibling();
		}
		if (p == 0) {
			while (en.nextElementSibling() != null) {
				if (en.nextElementSibling().tagName().equals(elm.tagName())) {
					n = 1;
					break;
				}
				en = en.nextElementSibling();
			}
		}
		if (p > 0) {
			return p + 1;
		} else if (n == 1) {
			return 1;
		} else
			return 0;
	}

	public static XPath GenXpathEleNonAttribute(Element elm) {
		XPath xpath = new XPath();
		if (elm.hasAttr("name")) {
			xpath.setElementName(elm.attr("name"));
		}
		StringBuilder path = new StringBuilder("/" + elm.tagName());
		if (checkSiblingIndexLikeTag(elm) != 0) {
			path.append("[" + checkSiblingIndexLikeTag(elm) + "]");
		}
		Elements p_elm = elm.parents();
		for (Element e : p_elm) {
			if (GenXpathByAttributeId(e) != null) {
				path.insert(0, GenXpathByAttributeId(e).getxPath());
				break;
			} else {
				if (checkSiblingIndexLikeTag(e) != 0) {
					path.insert(0, "/" + e.tagName() + "[" + checkSiblingIndexLikeTag(e) + "]");
				} else
					path.insert(0, "/" + e.tagName());
			}
		}
		xpath.setxPath(path.toString());

		return xpath;
	}

	@Override
	public Set<XPath> getXpath(String url) {
		Set<XPath> lstxPath = new HashSet<XPath>();
		try {
			String html = getHtml(url);
			Document doc = Jsoup.parse(html);
			Elements elements = doc.body().getAllElements();
			for (Element i : elements) {
				if (GenXpathByAttributeId(i) != null) {
					lstxPath.add(GenXpathByAttributeId(i));
				} else
					lstxPath.add(GenXpathEleNonAttribute(i));
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return lstxPath;
	}

	public String getXpath2(String url) {
		String html2 = "";
		try {
			String html = getHtml(url);
			Document doc = Jsoup.parse(html);
			Elements elements = doc.body().getAllElements();
			for (Element i : elements) {
				if (GenXpathByAttributeId(i) != null) i.attr("title", GenXpathByAttributeId(i).getxPath());
				else i.attr("title", GenXpathEleNonAttribute(i).getxPath());
			}
			Document doc2 = urlCss(doc,url);
			html2 = doc2.html();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return html2;
	}

	@Override
	public String getHtml(String url) {
		String a="";
		try {
			Document doc = Jsoup.connect(url).get();
			//Document doc2 = urlCss(doc,url);
			a = doc.html();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return a;
	}
	
//-----------------------------------------------------------------------------------
	
	public Document urlCss(Document doc, String url) {
		for (Element link : doc.select("link[rel=stylesheet]")) {
			String cssFilename = link.attr("href");
			link.attr("href",getCssPath(cssFilename, url));
		}
		return doc;
	}
	
	public static void getContent(String url) {
		try {
			URL yahoo = new URL(url);
			BufferedReader in = new BufferedReader(new InputStreamReader(yahoo.openStream()));
			String line;
			//BufferedWriter writer = new BufferedWriter(new FileWriter(new File("C:/test.html")));
			while ((line = in.readLine()) != null) {
				//writer.write(line);
				//writer.newLine();
				System.out.println(line);
			}
		} catch (Exception ex) {
		}
	}

	public static String getCssPath(String urlFile, String urlWeb) {
		String pathFile = "";
		if (urlFile.startsWith("http://") || urlFile.startsWith("https://")) {
			if (checkAvailable(urlFile)) {
				return urlFile;
			}
		} else {
			String[] parts = urlWeb.split("://");
			String[] HOST = parts[1].split("/");
			pathFile = parts[0] + "://" + HOST[0] + urlFile;
			if (checkAvailable(pathFile)) {
				return pathFile;
			}
		}
		return pathFile;
	}

	public static boolean checkAvailable(String urlPath) {
		boolean rs = false;
		try {
			URL u = new URL(urlPath);
			HttpURLConnection huc = (HttpURLConnection) u.openConnection();
			huc.setRequestMethod("GET");
			huc.connect();
			if (huc.getResponseCode() == 200) {
				rs = true;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rs;
	}

}
