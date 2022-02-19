package com.example.someappjava.articles.gui.v1;

import com.example.someappjava.articles.service.ArticleService;
import com.vaadin.flow.component.html.Article;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("articles")
public class ArticleGui extends VerticalLayout {

    private final ArticleService articleService;

    public ArticleGui(final ArticleService articleService) {
        this.articleService = articleService;


        Label label = new Label();
        label.setText("Hello world");
        add(label);

        articleService.getArticles().forEach(a -> {
            Article article = new Article();
            article.setTitle(a.getTitle());
            article.setText(a.getContent());
            add(article);
        });
    }
}
