package ru.lilitweb.books.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "questions")
public class QuestionsProperties {
    private Map<String, String> files = new HashMap<>();

    public Map<String, String> getFiles() {
        return files;
    }
}
