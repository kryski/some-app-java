package com.example.someappjava.articles.service;

import com.example.someappjava.articles.domain.Article;
import com.example.someappjava.articles.mapper.ArticleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ArticleRabbitSender {

    private final RabbitTemplate rabbitTemplate;
    private final ArticleMapper articleMapper;
    private final String exchangeName;
    private final String routingKey;

    public ArticleRabbitSender(
            final RabbitTemplate rabbitTemplate, final ArticleMapper articleMapper,
            @Value("${articles.rabbit.exchange}") final String exchangeName,
            @Value("${articles.rabbit.routing-key}") final String routingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.articleMapper = articleMapper;
        this.exchangeName = exchangeName;
        this.routingKey = routingKey;
    }

    public void notifyAboutArticleCreation(final Article article) {
        rabbitTemplate.convertAndSend(exchangeName, routingKey, articleMapper.map(article));
        log.info("Rabbit notification sent. Article title: " + article.getTitle());
    }
}
