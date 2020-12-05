package com.testing.cuatroEnLinea.configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
 

 
public class WebInitializer implements WebApplicationInitializer {

    @Autowired
    private ApplicationContext applicationContext;
 
    public void onStartup(ServletContext contenedor) throws ServletException {
 
        AnnotationConfigWebApplicationContext contexto = new AnnotationConfigWebApplicationContext();
        contexto.register(WebConfig.class);
        contexto.setServletContext(contenedor);
 
        ServletRegistration.Dynamic servlet = contenedor.addServlet("dispatcher", new DispatcherServlet(contexto));
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/*");
    }
 
}