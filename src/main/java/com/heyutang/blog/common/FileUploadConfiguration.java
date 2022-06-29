package com.heyutang.blog.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;
import java.io.File;

@Configuration
public class FileUploadConfiguration {

    @Value("${filePath}")
    private String filePath;

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();

        //设置最大上传文件大小
        factory.setMaxFileSize(DataSize.ofGigabytes(100));

        factory.setMaxRequestSize(DataSize.ofGigabytes(100));

        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdir();
        }

        factory.setLocation(filePath);
        return factory.createMultipartConfig();

    }
}
