package com.magrabbit.internship.xpath.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.magrabbit.internship.xpath.service.XPathService;

@Service
public class XPathServiceImpl implements XPathService {

	public static Elements getElements(String html) {
		Document doc = Jsoup.parse(html);
		Elements elements = doc.getAllElements();
		return elements;
	}

	public static String GenXpathByAttributeId(Element elm) {

		if (!elm.id().equals("")) {
			return "//*[@id='" + elm.attr("id") + "']";
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

	public static String GenXpathEleNonAttribute(Element elm) {
		StringBuilder path = new StringBuilder("/" + elm.tagName());
		if (checkSiblingIndexLikeTag(elm) != 0) {
			path.append("[" + checkSiblingIndexLikeTag(elm) + "]");
		}
		Elements p_elm = elm.parents();
		for (Element e : p_elm) {
			if (GenXpathByAttributeId(e) != null) {
				path.insert(0, GenXpathByAttributeId(e));
				break;
			} else {
				if (checkSiblingIndexLikeTag(e) != 0) {
					path.insert(0, "/" + e.tagName() + "[" + checkSiblingIndexLikeTag(e) + "]");
				} else
					path.insert(0, "/" + e.tagName());
			}
		}

		return path.toString();
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
						i.attr("xpath", GenXpathByAttributeId(i));
					} else {
						i.attr("xpath", GenXpathEleNonAttribute(i));
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
						"        z-index: 2147483647;\n" + 
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
						"	z-index: 2147483646;\n" + 
						"}\n" + 
						".myalert {\n" + 
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
						".myalert-success {\n" + 
						"color: #468847;\n" + 
						"background-color: #dff0d8;\n" + 
						"border-color: #d6e9c6;\n" + 
						"}\n" + 
						".mybtn-primary {\n" + 
						"    color: #fff;\n" + 
						"    background-color: #337ab7;\n" + 
						"    border-color: #2e6da4;\n" + 
						"}\n" + 
						".mybtn {\n" + 
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
						".myinput-group-btn {\n" + 
						"    position: relative;\n" + 
						"    font-size: 0;\n" + 
						"    white-space: nowrap;\n" + 
						"}\n" + 
						".myinput-group {\n" + 
						"    position: relative;\n" + 
						"    display: -webkit-box;\n" + 
						"    display: -webkit-flex;\n" + 
						"    display: -ms-flexbox;\n" + 
						"    display: flex;\n" + 
						"    width: 100%;\n" + 
						"}\n" + 
						".myform-inline {\n" + 
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
						"	if($bottomM <= $hShowxpath){\n" + 
						"      $topM-=($hShowxpath-2); \n" + 
						"    }else $topM-=2; \n" + 
						"	if($rightM < $wShowxpath){\n" + 
						"      $leftM-=($wShowxpath-2); \n" + 
						"	  $button1.css({display:'none'});\n" + 
						"	  $button2.css({display:'inline-block'});\n" + 
						"    }else{\n" + 
						"	  $leftM-=2;\n" + 
						"	  $button1.css({display:'inline-block'});\n" + 
						"	  $button2.css({display:'none'});\n" + 
						"	}\n" + 
						"    $showxpath.css({left: $leftM, top: $topM, display:'inline'});\n" + 
						"	if($rightM < 2){\n" + 
						"		$showxpath.css({display:'none'});\n" + 
						"	}\n" + 
						"\n" + 
						"  }).click(function(){\n" + 
						"	 $( \"#msg\" ).slideDown(1000);\n" + 
						"	 setTimeout(function(){\n" + 
						"		$( \"#msg\" ).slideUp(1000);\n" + 
						"	 }, 2000);\n" + 
						"	\n" + 
						"  }).mouseleave('hidexpath',function(){\n" + 
						"	$showxpath.css({display:'none'});\n" + 
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
						"<div class=\"myform-inline\" id=\"showxpath\">\n" + 
						"    <div class=\"myinput-group\" id=\"button1\">\n" + 
						"		<div class=\"myinput-group-btn\">\n" + 
						"			<button class=\"mybtn mybtn-primary\" id=\"copyxpath\" onclick=\"copyToClipboard('#xpath')\">copy</button>\n" + 
						"		</div>\n" + 
						"	</div>\n" + 
						"	<span id=\"xpath\"></span>\n" + 
						"	<div class=\"myinput-group\" id=\"button2\">\n" + 
						"		<div class=\"myinput-group-btn\">\n" + 
						"			<button class=\"mybtn mybtn-primary\" id=\"copyxpath\" onclick=\"copyToClipboard('#xpath')\">copy</button>\n" + 
						"		</div>\n" + 
						"	</div>\n" + 
						"</div>\n" + 
						"\n" + 
						"<div id=\"msg\" class=\"myalert myalert-success\">\n" + 
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
			link.attr("href", getPath(cssFilename, url));
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

	public static String getPath(String urlFile, String urlWeb) {
		String pathFile = "";
		if (urlFile.startsWith("http://") || urlFile.startsWith("https://") || urlFile.startsWith("//")) {
			return urlFile;
		} else {
				try {
					if(urlFile.startsWith("/")) {
						pathFile = getDomainName(urlWeb) + urlFile;
						return pathFile;
					}else {
						while(urlFile.startsWith("../")) {
							urlFile = urlFile.substring(3);
						}
						pathFile = getDomainName(urlWeb) + "/" + urlFile;
						return pathFile;
					}
					
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
	
	public Document parseLink(Document doc,String url) {
		for (Element links : doc.select("link[rel=stylesheet],link[as=style]")) {
			String link = links.attr("href");
			links.attr("href", getPath(link, url));
		}
		for (Element links : doc.select("img[src]")) {
			String link = links.attr("src");
			links.attr("src", getPath(link, url));
		}
		return doc;
	}
	
	public static String getDomainName(String url) throws MalformedURLException {
	    URL uri = new URL(url);
	    String protocol = uri.getProtocol();
	    int port = uri.getPort();
	    String domain = uri.getHost();
	    if(port==-1) return protocol +"://"+ domain;
	    else return protocol + "://" + domain + ":" + port; 
	}
}
