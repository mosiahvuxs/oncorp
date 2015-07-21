package br.com.oncorp.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ApplicationContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {
		
		System.getProperties().put("org.apache.el.parser.COERCE_TO_ZERO", "false");

	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		
	}

}
