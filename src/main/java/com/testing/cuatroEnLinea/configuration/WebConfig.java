// package com.testing.cuatroEnLinea.configuration;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.servlet.ViewResolver;
// import org.springframework.web.servlet.view.InternalResourceViewResolver;
// import org.springframework.web.servlet.view.UrlBasedViewResolver;

// @Configuration
// public class WebConfig {

//     public static final String RESOLVER_PREFIX= "/resources/static/";
//     public static final String RESOLVER_SUFIX=".html";

//     @Bean
//     public ViewResolver viewResolver(){
//         UrlBasedViewResolver viewResolver=new InternalResourceViewResolver();
//         viewResolver.setPrefix(RESOLVER_PREFIX);
//         viewResolver.setSuffix(RESOLVER_SUFIX);

//         return viewResolver;
//     }
// }

package com.testing.cuatroEnLinea.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan(basePackages = {"com.testing.cuatroEnLinea.*"})
public class WebConfig implements WebMvcConfigurer {


    @Bean
    public InternalResourceViewResolver templateResolver() {

        InternalResourceViewResolver resolver = new InternalResourceViewResolver();

        return resolver;
    }



}
