package com.newspaper.newspaper.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.newspaper.newspaper.repository.ArticleRepository;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public ResponseEntity<String> createArticle() {
        // Example logic to create an article using the repository
        articleRepository.save(new Article()); // Assuming an Article class exists
        return ResponseEntity.ok("Article created successfully");
    }
}
