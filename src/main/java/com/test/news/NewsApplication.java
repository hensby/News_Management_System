package com.test.news;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class NewsApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewsApplication.class, args);
    }

    @Configuration
    static class WebMvcConfigurer extends WebMvcConfigurerAdapter {
        //add interceptor
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(new MyInterceptor())    //Specify interceptor class
                    .addPathPatterns("/**");        //Specify url of interceptor class
        }
    }
}
