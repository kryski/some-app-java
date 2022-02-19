package com.example.someappjava.articles.service;

import com.example.someappjava.articles.api.v1.dto.Article;
import com.example.someappjava.articles.mapper.ArticleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import static com.example.someappjava.config.RabbitConfig.RABBIT_LISTENER_CONTAINER_FACTORY;

@Component
@Slf4j
public class ArticleNotificationRabbitListener {

    private final ArticleKafkaSender articleKafkaSender;

    public ArticleNotificationRabbitListener(ArticleKafkaSender articleKafkaSender) {
        this.articleKafkaSender = articleKafkaSender;
    }

    @RabbitListener(id = "articleCreationNotificationListener", queues = "${articles.rabbit.queue}", containerFactory = RABBIT_LISTENER_CONTAINER_FACTORY)
    public void processNotification(final Message<Article> notification) {
        final Article article = notification.getPayload();

        log.info("Received Rabbit notification about article creation, Title: " + article.getTitle());

        articleKafkaSender.notifyAboutArticleCreation(article);
        log.info("Notification resent to Kafka");
    }
}
