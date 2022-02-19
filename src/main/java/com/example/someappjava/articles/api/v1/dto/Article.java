package com.example.someappjava.articles.api.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    @NotNull
    private String title;
    @NotNull
    private String content;
}
