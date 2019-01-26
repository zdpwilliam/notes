package com.william;

import com.william.filter.UserFilter;
import com.william.interceptor.UserInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Hello world!
 */
@EnableAutoConfiguration
@ComponentScan("com.william")
public class SpringApplicationStarter extends SpringBootServletInitializer {

    /* @Bean // 过滤器
    public FilterRegistrationBean secureFilter() {
        return new FilterRegistrationBean(new UserFilter());
    }*/

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringApplicationStarter.class);
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new UserInterceptor()).addPathPatterns("/*");
//    }

    public static void main(String[] args) {
        SpringApplication.run(SpringApplicationStarter.class, args);
    }
}
