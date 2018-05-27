package ru.kpfu.itis.group11501.smartmuseum.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by volkov on 26.05.2018.
 */
@Configuration
public class CoreConfig {

    @Configuration
    @Profile("heroku")
    @PropertySource(value = {"classpath:persistence-heroku.properties", "classpath:uploadfile-heroku.properties"})
    static class Heroku{}

    @Configuration
    @Profile("default")
    @PropertySource(value = {"classpath:persistence.properties", "classpath:uploadfile.properties"}, ignoreResourceNotFound = true)
    static class Defaults{}

}