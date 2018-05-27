package ru.kpfu.itis.group11501.smartmuseum.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
import ru.kpfu.itis.group11501.smartmuseum.util.UserLogger;

import java.util.Properties;

/**
 * Created by Bogdan Popov on 05.11.2017.
 */
@Configuration
@EnableWebMvc
@ComponentScan("ru.kpfu.itis.group11501.smartmuseum")
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public FreeMarkerViewResolver viewResolver() {
        FreeMarkerViewResolver viewResolver = new FreeMarkerViewResolver();
        viewResolver.setCache(false);
        viewResolver.setPrefix("");
        viewResolver.setSuffix(".ftl");
        viewResolver.setContentType("text/html;charset=UTF-8");
        return viewResolver;
    }

    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setTemplateLoaderPath("/WEB-INF/templates");
        configurer.setDefaultEncoding("UTF-8");
        configurer.setFreemarkerSettings(new Properties(){{
            this.put("default_encoding", "UTF-8");
        }});
        return configurer;
    }

    @Bean
    @Autowired
    public freemarker.template.Configuration freeMarkerConfiguration(
            FreeMarkerConfig configurer) {
        return configurer.getConfiguration();
    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver createMultipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("utf-8");
        return resolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/*")
                .addResourceLocations("/resources/static/js/")
                .setCachePeriod(86400);
        registry.addResourceHandler("/css/*")
                .addResourceLocations("/resources/static/css/")
                .setCachePeriod(86400);
        registry.addResourceHandler("/img/*")
                .addResourceLocations("/resources/static/img/")
                .setCachePeriod(86400);
    }

    @Bean
    SessionManager getSessionManager() {
        return new SessionManager();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getSessionManager())
                .addPathPatterns("/**")
                .excludePathPatterns("/sign_in", "/logout", "/error");
    }
}
