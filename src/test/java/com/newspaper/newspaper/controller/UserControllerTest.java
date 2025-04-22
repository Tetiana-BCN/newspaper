package com.newspaper.newspaper.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.newspaper.newspaper.model.Article;
import com.newspaper.newspaper.model.Category;
import com.newspaper.newspaper.model.User;
import com.newspaper.newspaper.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        private UserRepository userRepository;

        @BeforeEach
        void setUp() {
                objectMapper.registerModule(new JavaTimeModule());
                userRepository.deleteAll();
        }

        @Test
        void testCreateUser() throws Exception {
                User user = new User(null, "Mimi", "superwoman@example.com");

                mockMvc.perform(post("/api/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(user)))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.name").value("Mimi"))
                                .andExpect(jsonPath("$.email").value("superwoman@example.com"));
        }

        @Test
        void testCreateUserWithArticles() throws Exception {
                User user = new User(null, "Milena", "okoro@example.com");

                ArrayList<Article> articles = new ArrayList<>();
                articles.add(new Article(
                                "Barcelona impulsa las startups",
                                "Barcelona se posiciona como uno de los principales ecosistemas de startups en Europa, atrayendo inversión extranjera.",
                                Category.STARTUPS,
                                LocalDate.now(),
                                null));

                articles.add(new Article(
                                "Industria gamer en alza",
                                "El sector de los videojuegos en España crece rápidamente, impulsado por nuevas startups locales y apoyo institucional.",
                                Category.VIDEOJUEGOS,
                                LocalDate.now(),
                                null));

                user.setArticles(articles);

                mockMvc.perform(post("/api/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(user)))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.name").value("Milena"))
                                .andExpect(jsonPath("$.articles", hasSize(2)))
                                .andExpect(jsonPath("$.articles[0].title").value("Barcelona impulsa las startups"))
                                .andExpect(jsonPath("$.articles[1].title").value("Industria gamer en alza"));
        }

        @Test
        void testGetAllUsers() throws Exception {
                User user1 = new User(null, "Mimi", "superwoman@example.com");
                User user2 = new User(null, "Tetiana", "tetiena@example.com");

                userRepository.saveAll(Arrays.asList(user1, user2));

                mockMvc.perform(get("/api/v1/users"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(2)))
                                .andExpect(jsonPath("$[0].name").value("Mimi"))
                                .andExpect(jsonPath("$[1].name").value("Tetiana"));
        }

        @Test
        void testGetUserById() throws Exception {
                User user = new User(null, "Mariana", "mariana@example.com");
                user = userRepository.save(user);

                mockMvc.perform(get("/api/v1/users/{id}", user.getId()))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").value(user.getId()))
                                .andExpect(jsonPath("$.name").value("Mariana"))
                                .andExpect(jsonPath("$.email").value("mariana@example.com"));
        }

        @Test
        void testGetUserById_NotFound() throws Exception {
                mockMvc.perform(get("/api/v1/users/999"))
                                .andExpect(status().isNotFound());
        }

        @Test
        void testUpdateUser() throws Exception {
                User user = new User(null, "Eva", "eva@example.com");
                user = userRepository.save(user);

                user.setName("Eva Actualizado");
                user.setEmail("eva.actualizado@example.com");

                mockMvc.perform(put("/api/v1/users/{id}", user.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(user)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.name").value("Eva Actualizado"))
                                .andExpect(jsonPath("$.email").value("eva.actualizado@example.com"));
        }

        @Test
        void testDeleteUser() throws Exception {
                User user = new User(null, "Miriam", "miriam@example.com");
                user = userRepository.save(user);

                mockMvc.perform(delete("/api/v1/users/{id}", user.getId()))
                                .andExpect(status().isNoContent());

                mockMvc.perform(get("/api/v1/users/{id}", user.getId()))
                                .andExpect(status().isNotFound());
        }
}
