package co.salutary.mobisaude.model;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class Factory {

    private ApplicationContext appContext = null;
    static private Factory factory = null;

    static public Factory getInstance() {
        if (factory == null)
            factory = new Factory();
        return factory;
    }
    
    private Factory() {
    }
 
    public void setApplicationContext(ServletContext sc) {
    	appContext = WebApplicationContextUtils.getWebApplicationContext(sc);
    }
    
    public void setApplicationContext(ApplicationContext ac) {
    	this.appContext = ac;
    }

    public ApplicationContext getApplicationContext() {
    	return appContext;
    }
    
    public Object get(String nome) {
    	if (appContext == null) {
    		appContext = new ClassPathXmlApplicationContext("META-INF/persistenceContext.xml");
    	}
        return appContext.getBean(nome);
    }
}