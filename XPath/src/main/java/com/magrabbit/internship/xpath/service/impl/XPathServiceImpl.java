package com.magrabbit.internship.xpath.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

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

	// @Override
	// public String insertXpathDatabase(int id, XPath xpath) {
	// String mess = "";
	// System.out.println("insert xpath" + xpath.getElementName());
	// if (this.xPathDao.save(id, xpath)) {
	// mess = "Insert OK !!!";
	// } else {
	// mess = "Insert Failed !!!";
	// }
	// return mess;
	// }

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
	public List<XPath> getXpath(String url) {
		List<XPath> lstxPath = new ArrayList<XPath>();
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
		
		String html = getHtml(url);
		return genXpath(url,html);
	}
	
	@Override
	public String getXpath3(String url, String html) {
		return genXpath(url,html);
	}

	String genXpath(String url,String html) {
		String html2 = "";
		if (html != null) {
			try {
				Document doc = Jsoup.parse(html);
				Elements elements = doc.body().select("*");
				for (Element i : elements) {
					if (GenXpathByAttributeId(i) != null) {
						i.attr("xpath", GenXpathByAttributeId(i).getxPath());
					} else {
						i.attr("xpath", GenXpathEleNonAttribute(i).getxPath());
					}
				}
				Document doc2 = parseLink(doc,url);
				html2 = doc2.html();
				String append2 = "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js\"></script>\n" + 
						"<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script>\n" + 
						"<style type=\"text/css\">\n" + 
						"#showxpath{\n" + 
						"        position:fixed;\n" + 
						"        display:none;\n" + 
						"        background: white;\n" + 
						"        border: 1px solid gray;\n" + 
						"        z-index: 9999;\n" + 
						"}\n" + 
						"#copyxpath{\n" + 
						"        background: #563d7c;\n" + 
						"        color: white;\n" + 
						"        height: 25px;\n" + 
						"        width: 40px;\n" + 
						"		padding-top:1px;\n" + 
						"		padding-left:4px;\n" + 
						"}\n" + 
						"#msg{	\n" + 
						"	position: fixed;\n" + 
						"    top: 10%;\n" + 
						"    left: 50%;\n" + 
						"    transform: translate(-50%, -50%);\n" + 
						"    display: none;\n" + 
						"z-index: 9999;\n" +
						"}\n" + 
						".alert {\n" + 
						"padding: 8px 35px 8px 14px;\n" + 
						"margin-bottom: 18px;\n" + 
						"color: #c09853;\n" + 
						"text-shadow: 0 1px 0 rgba(255, 255, 255, 0.5);\n" + 
						"background-color: #fcf8e3;\n" + 
						"border: 1px solid #fbeed5;\n" + 
						"-webkit-border-radius: 4px;\n" + 
						"-moz-border-radius: 4px;\n" + 
						"border-radius: 4px;\n" + 
						"}\n" + 
						".alert-success {\n" + 
						"color: #468847;\n" + 
						"background-color: #dff0d8;\n" + 
						"border-color: #d6e9c6;\n" + 
						"}\n" + 
						".btn-primary {\n" + 
						"    color: #fff;\n" + 
						"    background-color: #337ab7;\n" + 
						"    border-color: #2e6da4;\n" + 
						"}\n" + 
						".btn {\n" + 
						"    display: inline-block;\n" + 
						"    padding: 6px 12px;\n" + 
						"    margin-bottom: 0;\n" + 
						"    font-size: 14px;\n" + 
						"    font-weight: 400;\n" + 
						"    line-height: 1.42857143;\n" + 
						"    text-align: center;\n" + 
						"    white-space: nowrap;\n" + 
						"    vertical-align: middle;\n" + 
						"    -ms-touch-action: manipulation;\n" + 
						"    touch-action: manipulation;\n" + 
						"    cursor: pointer;\n" + 
						"    -webkit-user-select: none;\n" + 
						"    -moz-user-select: none;\n" + 
						"    -ms-user-select: none;\n" + 
						"    user-select: none;\n" + 
						"    background-image: none;\n" + 
						"    border: 1px solid transparent;\n" + 
						"    border-radius: 4px;\n" + 
						".input-group-btn {\n" + 
						"    position: relative;\n" + 
						"    font-size: 0;\n" + 
						"    white-space: nowrap;\n" + 
						"}\n" + 
						".input-group {\n" + 
						"    position: relative;\n" + 
						"    display: -webkit-box;\n" + 
						"    display: -webkit-flex;\n" + 
						"    display: -ms-flexbox;\n" + 
						"    display: flex;\n" + 
						"    width: 100%;\n" + 
						"}\n" + 
						".form-inline {\n" + 
						"    display: -webkit-box;\n" + 
						"    display: -webkit-flex;\n" + 
						"    display: -ms-flexbox;\n" + 
						"    display: flex;\n" + 
						"    -webkit-flex-flow: row wrap;\n" + 
						"    -ms-flex-flow: row wrap;\n" + 
						"    flex-flow: row wrap;\n" + 
						"    -webkit-box-align: center;\n" + 
						"    -webkit-align-items: center;\n" + 
						"    -ms-flex-align: center;\n" + 
						"    align-items: center;\n" + 
						"}\n" + 
						"}\n" + 
						"</style>\n" + 
						"<script>\n" + 
						"$(function(){\n" + 
						"  var $showxpath=$('#showxpath');\n" + 
						"  var $button1=$('#button1');\n" + 
						"  var $button2=$('#button2');\n" + 
						"  $(window).mouseenter('showxpath', function(e){\n" + 
						"    var $xpath = $(e.target).attr('xpath');\n" + 
						"    $(\"#xpath\").text($xpath);\n" + 
						"	var $wShowxpath=$showxpath.outerWidth();\n" + 
						"	var $hShowxpath=$showxpath.outerHeight();\n" + 
						"\n" + 
						"    var $leftM=e.clientX, $topM=e.clientY;\n" + 
						"    var $rightM=$(this).width()-$leftM;\n" + 
						"    var $bottomM=$(this).height()-$topM;\n" + 
						"	if($bottomM < $hShowxpath){\n" + 
						"      $topM-=$hShowxpath; \n" + 
						"    }\n" + 
						"	if($rightM < $wShowxpath){\n" + 
						"      $leftM-=$wShowxpath; \n" + 
						"	  $button1.css({display:'none'});\n" + 
						"	  $button2.css({display:'inline-block'});\n" + 
						"    }else{\n" + 
						"	  $button1.css({display:'inline-block'});\n" + 
						"	  $button2.css({display:'none'});\n" + 
						"	}\n" + 
						"    $showxpath.css({left: $leftM, top: $topM, display:'inline'});\n" + 
						"\n" + 
						"\n" + 
						"  }).click(function(){\n" + 
						"	 $( \"#msg\" ).slideDown(1000);\n" + 
						"	 setTimeout(function(){\n" + 
						"		$( \"#msg\" ).slideUp(1000);\n" + 
						"	 }, 2000);\n" + 
						"	\n" + 
						"  })\n" + 
						"	\n" + 
						"});\n" + 
						"function copyToClipboard(element) {\n" + 
						"  var $temp = $(\"<input>\");\n" + 
						"  $(\"body\").append($temp);\n" + 
						"  $temp.val($(element).text()).select();\n" + 
						"  document.execCommand(\"copy\");\n" + 
						"  $temp.remove();\n" + 
						"  \n" + 
						"}\n" + 
						"</script>\n" + 
						"<div class=\"form-inline\" id=\"showxpath\">\n" + 
						"    <div class=\"input-group\" id=\"button1\">\n" + 
						"		<div class=\"input-group-btn\">\n" + 
						"			<button class=\"btn btn-primary\" id=\"copyxpath\" onclick=\"copyToClipboard('#xpath')\">copy</button>\n" + 
						"		</div>\n" + 
						"	</div>\n" + 
						"	<span id=\"xpath\"></span>\n" + 
						"	<div class=\"input-group\" id=\"button2\">\n" + 
						"		<div class=\"input-group-btn\">\n" + 
						"			<button class=\"btn btn-primary\" id=\"copyxpath\" onclick=\"copyToClipboard('#xpath')\">copy</button>\n" + 
						"		</div>\n" + 
						"	</div>\n" + 
						"</div>\n" + 
						"\n" + 
						"<div id=\"msg\" class=\"alert alert-success\">\n" + 
						"    <strong>Xpath has been copied to clipboard.</strong>\n" + 
						"</div>";
				html2 = html2 + "\n" + append2;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return html2;
	}
	
	@Override
	public String getHtml(String url) {
		String a = null;
		try {
			Document doc = Jsoup.connect(url).get();
			// Document doc2 = urlCss(doc,url);
			a = doc.outerHtml();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return a;
	}

	// -----------------------------------------------------------------------------------

	public Document urlCss(Document doc, String url) {
		for (Element link : doc.select("link[rel=stylesheet]")) {
			String cssFilename = link.attr("href");
			link.attr("href", getCssPath(cssFilename, url));
		}
		return doc;
	}

	public static void getContent(String url) {
		try {
			URL yahoo = new URL(url);
			BufferedReader in = new BufferedReader(new InputStreamReader(yahoo.openStream()));
			String line;
			// BufferedWriter writer = new BufferedWriter(new FileWriter(new
			// File("C:/test.html")));
			while ((line = in.readLine()) != null) {
				// writer.write(line);
				// writer.newLine();
				System.out.println(line);
			}
		} catch (Exception ex) {
		}
	}

	public static String getCssPath(String urlFile, String urlWeb) {
		String pathFile = "";
		if (urlFile.startsWith("http://") || urlFile.startsWith("https://") || urlFile.startsWith("//")) {
			return urlFile;
		} else {
				String[] parts = urlWeb.split("://");
				String[] HOST = parts[1].split("/");
				pathFile = parts[0] + "://" + HOST[0] + urlFile;
				if (pathFile.startsWith("http://")) {
					//System.out.print(pathFile);
					//if (checkAvailableHttp(pathFile)) 
					return pathFile;
				}
				if (pathFile.startsWith("https://")) {
					//System.out.print(pathFile);
					//if (checkAvailableHttps(pathFile)) 
					return pathFile;
				}
			}
		return urlFile;
	}

	public static boolean checkAvailableHttp(String urlPath) {
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

	public static boolean checkAvailableHttps(String urlPath) {
		boolean rs = false;
		try {
			URL u = new URL(urlPath);
			HttpsURLConnection huc = (HttpsURLConnection) u.openConnection();
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
	
	//---------------------------------------------------------------
	
	public Document urlImg(Document doc, String url) {
		for (Element img : doc.select("img")) {
			String imgFilename = img.attr("src");
			img.attr("src", getCssPath(imgFilename, url));
		}
		return doc;
	}
	
	public Document parseLink(Document doc,String url) {
		for (Element links : doc.select("link[rel=stylesheet],link[as=style]")) {
			String link = links.attr("href");
			links.attr("href", getCssPath(link, url));
		}
		for (Element links : doc.select("img[src]")) {
			String link = links.attr("src");
			links.attr("src", getCssPath(link, url));
		}
		return doc;
	}
}
