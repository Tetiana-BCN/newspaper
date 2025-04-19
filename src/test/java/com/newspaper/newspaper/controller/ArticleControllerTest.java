package com.newspaper.newspaper.controller;

import java.time.LocalDate;
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
import com.newspaper.newspaper.repository.ArticleRepository;
import com.newspaper.newspaper.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ArticleControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        private ArticleRepository articleRepository;

        @Autowired
        private UserRepository userRepository;

        private User testUser;

        @BeforeEach
        void setUp() {
                objectMapper.registerModule(new JavaTimeModule());
                articleRepository.deleteAll();
                userRepository.deleteAll();

                testUser = new User(null, "Eva Test", "eva@test.com");
                testUser = userRepository.save(testUser);
        }

        @Test
        void testCreateArticle() throws Exception {
                Article article = new Article(
                                "El Barça clasifica a semis de Champions",
                                "Con goles de Gündogan y Raphinha, el FC Barcelona venció al PSG y clasificó a semifinales por primera vez en años.",
                                Category.DEPORTES,
                                LocalDate.now(),
                                testUser);

                mockMvc.perform(post("/api/v1/article")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(article)))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.title").value("El Barça clasifica a semis de Champions"))
                                .andExpect(jsonPath("$.category").value("DEPORTES"));
        }

        @Test
        void testGetAllArticles() throws Exception {
                Article article1 = new Article(
                                "Flick confirma que sigue como DT del Barça",
                                "Flick Hernández ha anunciado que continuará como entrenador del FC Barcelona la próxima temporada.",
                                Category.DEPORTES,
                                LocalDate.now(),
                                testUser);

                Article article2 = new Article(
                                "Lanzamiento del nuevo FC Barcelona eFootball",
                                "Konami lanza una edición especial de eFootball con licencias exclusivas del FC Barcelona y modo historia de La Masía.",
                                Category.VIDEOJUEGOS,
                                LocalDate.now(),
                                testUser);

                articleRepository.saveAll(Arrays.asList(article1, article2));

                mockMvc.perform(get("/api/v1/article"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(2)))
                                .andExpect(jsonPath("$[0].title").value("Flick confirma que sigue como DT del Barça"))
                                .andExpect(jsonPath("$[1].title")
                                                .value("Lanzamiento del nuevo FC Barcelona eFootball"));
        }

        @Test
        void testGetArticleById() throws Exception {
                Article article = new Article(
                                "Lamine Yamal brilla en Champions",
                                "El joven talento azulgrana fue clave en la victoria con dos asistencias y una madurez sorprendente para su edad.",
                                Category.DEPORTES,
                                LocalDate.now(),
                                testUser);

                article = articleRepository.save(article);

                mockMvc.perform(get("/api/v1/article/{id}", article.getId()))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.title").value("Lamine Yamal brilla en Champions"))
                                .andExpect(jsonPath("$.category").value("DEPORTES"));
        }

        @Test
        void testUpdateArticle() throws Exception {
                Article article = new Article(
                                "Novedades sobre el nuevo estadio",
                                "El FC Barcelona avanza con la renovación del Spotify Camp Nou, que contará con tecnología de punta y mayor aforo.",
                                Category.DEPORTES,
                                LocalDate.now(),
                                testUser);

                article = articleRepository.save(article);
                article.setTitle("Renovación del Camp Nou en marcha");

                mockMvc.perform(put("/api/v1/article/{id}", article.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(article)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.title").value("Renovación del Camp Nou en marcha"));
        }

        @Test
        void testDeleteArticle() throws Exception {
                Article article = new Article(
                                "Videojuego retro del Barça vuelve en 2025",
                                "Se relanza un clásico juego del FC Barcelona para consolas modernas, con gráficos pixelados y leyendas del club.",
                                Category.VIDEOJUEGOS,
                                LocalDate.now(),
                                testUser);

                article = articleRepository.save(article);

                mockMvc.perform(delete("/api/v1/article/{id}", article.getId()))
                                .andExpect(status().isNoContent());

                mockMvc.perform(get("/api/v1/article/{id}", article.getId()))
                                .andExpect(status().isNotFound());
        }
}
