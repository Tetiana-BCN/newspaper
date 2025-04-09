package com.newspaper.newspaper.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.newspaper.newspaper.model.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
}