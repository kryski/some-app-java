package com.example.someappjava.articles.client;

import com.example.someappjava.articles.domain.Article;
import com.example.someappjava.articles.mapper.ArticleMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ArticleRestClient {

    private final RestTemplate restTemplate;
    private final ArticleMapper articleMapper;

    public ArticleRestClient(RestTemplate restTemplate, ArticleMapper articleMapper) {
        this.restTemplate = restTemplate;
        this.articleMapper = articleMapper;
    }

    public void saveArticle(final Article article) {
        saveArticle(articleMapper.map(article));
    }

    public void saveArticle(final com.example.someappjava.articles.api.v1.dto.Article article) {
        restTemplate.exchange("http://localhost:8080/rest/v1/articles", HttpMethod.POST, new HttpEntity<>(article), Void.class);
    }
}
