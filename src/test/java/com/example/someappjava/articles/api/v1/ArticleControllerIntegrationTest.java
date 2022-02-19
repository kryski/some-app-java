package com.example.someappjava.articles.api.v1;

import com.example.someappjava.articles.api.v1.dto.Article;
import com.example.someappjava.articles.mapper.ArticleMapper;
import com.example.someappjava.articles.service.ArticleService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = ArticleController.class
)
class ArticleControllerIntegrationTest {
    private static final long ID = 1;
    private static final Article ARTICLE = new Article("Title", "Content");
    private static final List<Article> ARTICLES = List.of(ARTICLE);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ArticleService articleService;

    @MockBean
    private ArticleMapper articleMapper;

    @Test
    @DisplayName("Get all articles")
    void testGetArticles() throws Exception {
        when(articleMapper.map(anyList())).thenReturn(ARTICLES);

        final MvcResult mvcResult = mockMvc.perform(get("/rest/v1/articles")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final List<Article> actualArticles = asList(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Article[].class));

        assertThat(actualArticles).isEqualTo(ARTICLES);
    }

    @Test
    @DisplayName("Get article")
    void testGetArticle() throws Exception {
        when(articleMapper.map(nullable(com.example.someappjava.articles.domain.Article.class))).thenReturn(ARTICLE);

        final MvcResult mvcResult = mockMvc.perform(get("/rest/v1/articles/" + ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);
        verify(articleService).getArticle(idCaptor.capture());
        final Long actualId = idCaptor.getValue();

        final Article actualArticle = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Article.class);

        assertThat(actualId).isEqualTo(ID);
        assertThat(actualArticle).isEqualTo(ARTICLE);
    }

    @Test
    @DisplayName("Add article")
    void testAddArticle() throws Exception {
        final MvcResult mvcResult = mockMvc.perform(post("/rest/v1/articles")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(ARTICLE)))
                .andExpect(status().isOk())
                .andReturn();

        final ArgumentCaptor<Article> articleCaptor = ArgumentCaptor.forClass(Article.class);
        verify(articleMapper).map(articleCaptor.capture());
        final Article actualArticle = articleCaptor.getValue();

        assertThat(mvcResult.getResponse().getContentAsString()).isEmpty();
        assertThat(mvcResult.getResponse().getContentType()).isNull();
        assertThat(actualArticle).isEqualTo(ARTICLE);

    }

    @Test
    @DisplayName("Delete article")
    void testDeleteArticle() throws Exception {
        final MvcResult mvcResult = mockMvc.perform(delete("/rest/v1/articles/" + ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        final ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);
        verify(articleService).deleteArticle(idCaptor.capture());
        final Long actualId = idCaptor.getValue();

        assertThat(mvcResult.getResponse().getContentAsString()).isEmpty();
        assertThat(mvcResult.getResponse().getContentType()).isNull();
        assertThat(actualId).isEqualTo(ID);
    }
}