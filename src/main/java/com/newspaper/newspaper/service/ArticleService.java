package com.newspaper.newspaper.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.newspaper.newspaper.model.Article;
import com.newspaper.newspaper.repository.ArticleRepository;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article createArticle(Article article) {
        return articleRepository.save(article);
    }

    public ResponseEntity<String> createArticle() {
        articleRepository.save(new Article());
        return ResponseEntity.ok("Article created successfully");
    }

    public ResponseEntity<String> getArticles() {
        return ResponseEntity.ok("List of articles");
    }

    public ResponseEntity<String> updateArticle() {
        return ResponseEntity.ok("Article updated successfully");
    }

    public ResponseEntity<String> deleteArticle() {
        return ResponseEntity.ok("Article deleted successfully");
    }
}