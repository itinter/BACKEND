package common;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import dao.UrlDAO;
import model.Url;

public class app {
	public static void main( String[] args )
    {
    	ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");

        UrlDAO urlDAO = (UrlDAO) context.getBean("urlDAO");
        Url url = new Url("youtube.com");
        urlDAO.insert(url);

        System.out.println(url);

    }
}
