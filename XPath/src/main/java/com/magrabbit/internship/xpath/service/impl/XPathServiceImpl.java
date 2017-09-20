package com.magrabbit.internship.xpath.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magrabbit.internship.xpath.dao.XPathDAO;
import com.magrabbit.internship.xpath.models.XPath;
import com.magrabbit.internship.xpath.service.XPathService;
import com.magrabbit.internship.xpath.service.impl.XPathServiceImpl;

@Service
public class XPathServiceImpl implements XPathService{
	
	@Autowired
    XPathDAO xPathDao;

	@Override
	public ArrayList<String> getXpath(String url) {
		// TODO Auto-generated method stub
		return null;
	}

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
//	public static File getFileHtml(String url) throws IOException {
//		Document doc = Jsoup.connect(url).get();  
//	       String html = doc.outerHtml(); 
//	       File filehtml = new File("\\src\\main\\resources\\file\\html.html");
//	       FileWriter fw = new FileWriter(filehtml);
//	       BufferedWriter bw = new BufferedWriter(fw);
//	       bw.write(html);
//	       bw.close();
//	       fw.close();
//		return filehtml;
//	}
//	
//	public static Elements getElements(File file) throws IOException { 
//		Document doc = Jsoup.parse(file, "UTF-8");
//		//doc.getAllElements();
//		Elements Elements = doc.getAllElements();
//		return Elements;
//	}
//	
//	public static Attributes getAttributeOfElement(Element elm) {		
//			if(elm.attributes().asList().size() != 0) {
//				return elm.attributes();
//			}else return null;
//			
//	}
//	public static String getElementName(Element elm){
//		Attributes lstAttr = getAttributeOfElement(elm);
//		int space = elm.toString().indexOf(" ");
//		int brace = elm.toString().indexOf(">");
//		int endIndex = 0;
//		if(space < brace && space > -1) endIndex = space;
//		else endIndex = brace;
//		String elmname = elm.toString().substring(1, endIndex);
//		return elmname;
//	}
//	
//	
//	public static String isNormal(String str) {
//		str=str.trim();
//		while(str.indexOf("  ")>0) {
//			str=str.replace("  ", " ");
//		}
//		return str;
//	}
//	public static ArrayList<Attribute> divideAttribute(Attributes attrs) {
//		ArrayList<Attribute> listAttr = new ArrayList<Attribute>();
//		for(Attribute i:attrs) {
//			if(i.getKey().equals("id") || i.getKey().equals("class") || i.getKey().equals("name") || i.getKey().equals("href") || i.getKey().equals("src")) {
//				listAttr.add(i);
//			}
//		}
//		//String strAttrs = isNormal(attrs.toString());
//		//String[] listAttr = strAttrs.split(" ");
//		return listAttr;
//	}
//	
//	public static ArrayList<String> GenXpathByAttribute(Element elm){
//		ArrayList<String> lstXpath = new ArrayList<String>();
//		if(elm.attributes().asList().size() != 0) {
//			for(Attribute attr: divideAttribute(elm.attributes())) {
//				String xPath = "//"+getElementName(elm)+"[@"+ attr +"]";
//				lstXpath.add(xPath);
//			}
//		}return lstXpath;
//	}
//	
//	public ArrayList<String> getXpath(String url){
//		ArrayList<String> xPath = new ArrayList<String>();
//		Elements e;
//		try {
//			e = getElements(getFileHtml(url));
//			for(Element i:e) {
//				if(GenXpathByAttribute(i) != null) {
//					xPath.addAll(GenXpathByAttribute(i));
//				}
//			}
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		return xPath;
//	}
 
}
