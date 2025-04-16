package com.newspaper.newspaper.controller;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newspaper.newspaper.model.Article;
import com.newspaper.newspaper.model.Category;
import com.newspaper.newspaper.model.User;
import com.newspaper.newspaper.repository.ArticleRepository;
import com.newspaper.newspaper.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class ArticleControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private User testUser;

    @BeforeEach
    void setUp() {
        articleRepository.deleteAll();
        userRepository.deleteAll();

        testUser = new User();
        testUser.setName("Mimi Power");
        testUser.setEmail("powerwoman@example.com");
        userRepository.save(testUser);
    }

    @Test
    void createArticle_shouldReturn201_andSaveArticle() throws Exception {
        Article article = new Article();
        article.setTitle("Nuevo descubrimiento en Marte");
        article.setContent("La NASA ha encontrado signos de agua.");
        article.setCategory(Category.CULTURA);
        article.setPublicationDate(LocalDate.now());
        article.setUser(testUser);

        mockMvc.perform(post("/api/articles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(article)))
                .andExpect(status().isCreated());

        assertThat(articleRepository.findAll()).hasSize(1);
        assertThat(articleRepository.findAll().get(0).getUser().getEmail()).isEqualTo("eva@example.com");
    }

    @Test
    void setUp_shouldClearRepositories() {
        
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        userRepository.save(user);

        Article article = new Article();
        article.setTitle("Test Article");
        article.setContent("Test Content");
        article.setCategory(Category.CULTURA);
        article.setPublicationDate(LocalDate.now());
        article.setUser(user);
        articleRepository.save(article);

        setUp();
        
        assertThat(userRepository.findAll()).isEmpty();
        assertThat(articleRepository.findAll()).isEmpty();
    }
}