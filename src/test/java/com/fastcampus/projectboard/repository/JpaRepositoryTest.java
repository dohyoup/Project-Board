package com.fastcampus.projectboard.repository;

import com.fastcampus.projectboard.config.JpaConfig;
import com.fastcampus.projectboard.domain.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
@DataJpaTest
class JpaRepositoryTest {

    private final ArticleRepository articleRepository;

    private final ArticleCommentRepository articleCommentRepository;


    public JpaRepositoryTest(
            @Autowired ArticleRepository articleRepository,
            @Autowired ArticleCommentRepository articleCommentRepository
    ) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }

    @DisplayName("select 테스트")
    @Test
    void given_whenSelecting_thenWorksFine() {
       // Given

       // when
       List<Article> articles = articleRepository.findAll();
       // then
       assertThat(articles)
               .isNotNull()
               .hasSize(0);
    }

    @DisplayName("insert 테스트")
    @Test
    void given_whenInserting_thenWorksFine() {
       // Given
       long previousCount = articleRepository.count();

       // when
       Article savedArticles = articleRepository.save(Article.of("new article", "new content", "#spring"));
       // then
       assertThat(articleRepository.count())
               .isEqualTo(previousCount + 1);
    }

    @DisplayName("update 테스트")
    @Test
    void given_whenUpdating_thenWorksFine() {
       // Given
       articleRepository.save(Article.of("new article", "new content", "#spring"));
       Article article = articleRepository.findById(1L).orElseThrow();
       String updatedHashTag = "#springboot";
       article.setHashtag(updatedHashTag);
       // when
       Article newArticles = articleRepository.saveAndFlush(article);
       // then
       assertThat(newArticles).hasFieldOrPropertyWithValue("hashtag", updatedHashTag);
    }

    @DisplayName("delete 테스트")
    @Test
    void given_whenDeleting_thenWorksFine() {
       // Given
       articleRepository.save(Article.of("new article", "new content", "#spring"));// 데이터 추가
       Article article = articleRepository.findById(1L).orElseThrow(); // 추가한 데이터 가져오기
       long previousArticleCount = articleRepository.count();
       long previousArticleCommentCount = articleCommentRepository.count();
       int deletedCommentSize = article.getArticleComments().size();

       // when
       articleRepository.delete(article);
       // then
       assertThat(articleRepository.count()).isEqualTo(previousArticleCount - 1);
       assertThat(articleCommentRepository.count()).isEqualTo(previousArticleCommentCount - deletedCommentSize);
    }
}