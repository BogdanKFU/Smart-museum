package ru.kpfu.itis.group11501.smartmuseum.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import ru.kpfu.itis.group11501.smartmuseum.util.FileUploader;

/**
 * Created by volkov on 20.04.2018.
 */

@Configuration
public class FileUploadConfig {

    @Autowired
    private Environment env;

    @Bean
    public FileUploader fileUploader() {
        FileUploader fileUploader = new FileUploader();
        fileUploader.setVideoPath(env.getProperty("video.path"));
        fileUploader.setImagePath(env.getProperty("image.path"));
        return fileUploader;
    }



}