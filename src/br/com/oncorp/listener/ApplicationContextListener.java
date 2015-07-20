package br.com.oncorp.listener;

import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/*@WebListener*/
public class ApplicationContextListener implements ServletContextListener {

	@SuppressWarnings("rawtypes")
	@Override
	public void contextInitialized(ServletContextEvent event) {
		
		System.getProperties().put("org.apache.el.parser.COERCE_TO_ZERO", "false");
		
		ServletContext context = event.getServletContext();

        HashMap sessoesAtivas = new HashMap();
        
        context.setAttribute("sessoesAtivas", sessoesAtivas);
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		
	}

}
