package com.newspaper.newspaper.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public Article getArticleById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Article not found"));
    }

    public Article updateArticle(Long id, Article updatedArticle) {
        Article article = getArticleById(id);
        article.setTitle(updatedArticle.getTitle());
        article.setContent(updatedArticle.getContent());
        article.setCategory(updatedArticle.getCategory());
        article.setPublicationDate(updatedArticle.getPublicationDate());
        return articleRepository.save(article);
    }

    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }

}