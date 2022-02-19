package com.example.someappjava.articles.service;

import com.example.someappjava.articles.api.v1.dto.Article;
import com.example.someappjava.articles.mapper.ArticleMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ArticleKafkaSender {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ArticleMapper articleMapper;
    private final ObjectMapper objectMapper;
    private final String topicName;

    public ArticleKafkaSender(final KafkaTemplate<String, String> kafkaTemplate, final ArticleMapper articleMapper,
                              final ObjectMapper objectMapper,
                              @Value("${articles.kafka.topic}") final String topicName) {
        this.kafkaTemplate = kafkaTemplate;
        this.articleMapper = articleMapper;
        this.objectMapper = objectMapper;
        this.topicName = topicName;
    }

    public void notifyAboutArticleCreation(final com.example.someappjava.articles.domain.Article article) {
        notifyAboutArticleCreation(articleMapper.map(article));
    }

    @SneakyThrows
    public void notifyAboutArticleCreation(final Article article) {
        kafkaTemplate.send(topicName, objectMapper.writeValueAsString(article));
    }
}
