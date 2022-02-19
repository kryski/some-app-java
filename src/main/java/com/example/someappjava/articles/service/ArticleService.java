package com.example.someappjava.articles.service;

import com.example.someappjava.articles.client.ArticleRestClient;
import com.example.someappjava.articles.domain.Article;
import com.example.someappjava.articles.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleRestClient articleRestClient;
    private final ArticleRabbitSender articleRabbitSender;

    public ArticleService(ArticleRepository articleRepository, ArticleRestClient articleRestClient, ArticleRabbitSender articleRabbitSender) {
        this.articleRepository = articleRepository;
        this.articleRestClient = articleRestClient;
        this.articleRabbitSender = articleRabbitSender;
    }

    public List<Article> getArticles() {
        return articleRepository.findAll();
    }

    public Article getArticle(final Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public void createRandomArticle() {
        final Article article = new Article(null, RandomStringUtils.random(10), RandomStringUtils.random(20));
        articleRabbitSender.notifyAboutArticleCreation(article);
    }

    public Long saveArticle(final Article article) {
        final Article newArticle = articleRepository.save(article);

        log.info("Article ID: " + article.getId());

        return newArticle.getId();
    }

    public void deleteArticle(final Long id) {
        articleRepository.deleteById(id);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initDatabase() {
        final Article article = new Article(null, "Title", "Content");

        saveArticle(article);
    }
}
