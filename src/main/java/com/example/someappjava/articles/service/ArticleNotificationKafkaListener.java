package com.example.someappjava.articles.service;

import com.example.someappjava.articles.api.v1.dto.Article;
import com.example.someappjava.articles.client.ArticleRestClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ArticleNotificationKafkaListener {

    private final ArticleRestClient articleRestClient;
    private final ObjectMapper objectMapper;

    public ArticleNotificationKafkaListener(ArticleRestClient articleRestClient, ObjectMapper objectMapper) {
        this.articleRestClient = articleRestClient;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "${articles.kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
    @SneakyThrows
    public void processNotification(final String articleJson) {
        final Article article = objectMapper.readValue(articleJson, Article.class);
        articleRestClient.saveArticle(article);
        log.info("Received Kafka notification about article creation, Title: " + article.getTitle());
    }
}
