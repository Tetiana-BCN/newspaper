package com.newspaper.newspaper.controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.newspaper.newspaper.model.Article;

@RestController
@RequestMapping("/api/v1/article")

public class ArticleController {

    private final ArticleService articleService;(ArticleService articleService) {
        this.articleService = articleService;
    }
    
    @PostMapping
    public ResponseEntity<Article> createArticle(@RequestBody Article article) {
        Article createdArticle = articleService.createArticle(article);
        return new ResponseEntity<>(createdArticle, HttpStatus.CREATED);
    }
}
