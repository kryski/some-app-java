package com.example.someappjava.articles.repository;

import com.example.someappjava.articles.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
