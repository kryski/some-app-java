package com.example.someappjava.articles.api.v1;

import com.example.someappjava.articles.api.v1.dto.Article;
import com.example.someappjava.articles.mapper.ArticleMapper;
import com.example.someappjava.articles.service.ArticleService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/rest/v1/articles")
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleMapper articleMapper;

    public ArticleController(ArticleService articleService, ArticleMapper articleMapper) {
        this.articleService = articleService;
        this.articleMapper = articleMapper;
    }

    @GetMapping
    public List<Article> getArticles() {
        return articleMapper.map(articleService.getArticles());

    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Article getArticle(@PathVariable @NotNull Long id) {
        return articleMapper.map(articleService.getArticle(id));
    }

    @GetMapping("/random")
    public void createRandomArticle() {
        articleService.createRandomArticle();
    }

    @PostMapping
    public void addArticle(@RequestBody @Valid @NotNull final Article article) {
        articleService.saveArticle(articleMapper.map(article));
    }

    @DeleteMapping("/{id}")
    public void deleteArticle(@PathVariable @NotNull Long id) {
        articleService.deleteArticle(id);
    }
}
