package com.example.someappjava.articles.mapper;

import com.example.someappjava.articles.domain.Article;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", implementationName = "Default<CLASS_NAME>")
public interface ArticleMapper {

    com.example.someappjava.articles.api.v1.dto.Article map(Article article);

    Article map(com.example.someappjava.articles.api.v1.dto.Article article);

    List<com.example.someappjava.articles.api.v1.dto.Article> map(List<Article> article);
}
