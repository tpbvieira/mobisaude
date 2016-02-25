package co.salutary.util;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Classe que implementa os padroes <I>Factory</I> e <I>Singleton</I>. 
 * Responsavel pelo instanciamento de fachadas
 */

public class Factory {
    /**
     * ApplicationContext
     */
    private ApplicationContext appContext = null;
    /**
     * Instancia do singleton
     */
    static private Factory factory = null;
    
    /**
     * Obter a instancia unica
     * @return
     */
    static public Factory getInstance() {
        if (factory == null)
            factory = new Factory();
        return factory;
    }
    
    /** 
     * Construtor privado para implementar singleton
     */
    private Factory() {
    }
 
    /**
     * Setter de ServletContext
     * @param sc
     */
    public void setApplicationContext(ServletContext sc) {
    	appContext = WebApplicationContextUtils.getWebApplicationContext(sc);
    }
    
    /**
     * Setter de ApplicationContext
     * @param appContext
     */
    public void setApplicationContext(ApplicationContext ac) {
    	this.appContext = ac;
    }

    /**
     * Getter de ApplicationContext
     * @return
     */
    public ApplicationContext getApplicationContext() {
    	return appContext;
    }
    
    /**
     * Obter uma fachada
     * @param nome
     * @return
     */
    public Object get(String nome) {
    	if (appContext == null) {
    		appContext = new ClassPathXmlApplicationContext("META-INF/persistenceContext.xml");
    	}
        return appContext.getBean(nome);
    }
}