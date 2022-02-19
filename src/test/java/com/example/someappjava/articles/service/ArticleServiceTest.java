package com.example.someappjava.articles.service;

import com.example.someappjava.articles.domain.Article;
import com.example.someappjava.articles.repository.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private ArticleService articleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetArticles() {
        articleService.getArticles();

        verify(articleRepository).findAll();
        verifyNoMoreInteractions(articleRepository);
    }

    @Test
    void testGetArticle() {
        final long id = 1;

        articleService.getArticle(id);

        verify(articleRepository).findById(eq(id));
        verifyNoMoreInteractions(articleRepository);
    }

    @Test
    void testSaveArticle() {
        final Article article = new Article();
        article.setId(1L);

        when(articleRepository.save(eq(article))).thenReturn(article);

        articleService.saveArticle(article);

        verify(articleRepository).save(eq(article));
        verifyNoMoreInteractions(articleRepository);
    }

    @Test
    void testDeleteArticle() {
        final long id = 1;

        articleService.deleteArticle(id);

        verify(articleRepository).deleteById(eq(id));
        verifyNoMoreInteractions(articleRepository);
    }
}