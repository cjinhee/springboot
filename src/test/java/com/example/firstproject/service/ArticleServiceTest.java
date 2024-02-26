package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest //해당 클래스를 스프링 부트와 연동해 테스트
class ArticleServiceTest {

    @Autowired
    ArticleService articleService; // articleService 객체 주입
    @Test
    void index() {
        // 1. 예상 데이터 작성하기
        Article a = new Article(1L, "가가가가", "1111");
        Article b = new Article(2L, "나나나나","2222");
        Article c = new Article(3L, "다다다다", "3333");
        List<Article> expected = new ArrayList<Article>(Arrays.asList(a,b,c)); //a,b,c 합치기
        // 2. 실제 데이터 획득하기
        // articleService.index() 메서드를 호출해 그 결과를 List<Article> 타입의 article에 받아옴. 모든 게시글을 조회 요청하고 그 결과로 반환되는 게시글의 묶음을 받아옴
        List<Article> articles = articleService.index();
        // 3. 예상 데이터와 실제 데이터 비교해 검증하기
        assertEquals(expected.toString(), articles.toString());
    }

    @Test
    void show_성공_존재하는_id_입력() {
        // 1. 예상 데이터
        Long id = 1L;
        Article expected = new Article(id, "가가가가", "1111");
        // 2. 실제 데이터
        Article article = articleService.show(id); // 실제 데이터 저장
        // 3. 비교 및 검증
        assertEquals(expected.toString(), article.toString()); // 비교
    }

    @Test
    void show_실패_존재하지_않는_id_입력(){
        // 1. 예상 데이터
        Long id = -1L;
        Article expected = null;
        // 2. 실제 데이터
        Article article = articleService.show(id); // 실제 데이터 저장
        // 3. 비교 및 검증
        assertEquals(expected, article); // 비교
    }

    @Test
    @Transactional
    void create_성공_title과_content만_있는_dto_입력() {
        // 1. 예상 데이터
        String title = "라라라라";
        String content = "4444";
        ArticleForm dto = new ArticleForm(null, title, content); // dto 생성
        Article expected = new Article(4L, title, content); // 예상 데이터 저장
        // 2. 실제 데이터
        Article article = articleService.create(dto); // 실제 데이터 저장
        // 3. 비교 및 검증
        assertEquals(expected.toString(), article.toString()); // 비교
    }

    @Test
    @Transactional
    void create_실패_id가_포함된_dto_입력() {
        // 1. 예상 데이터
        Long id = 4L;
        String title = "라라라라";
        String content = "4444";
        ArticleForm dto = new ArticleForm(id, title, content); // dto 생성
        Article expected = null; // 예상 데이터 저장
        // 2. 실제 데이터
        Article article = articleService.create(dto); // 실제 생성 결과 저장
        // 3. 비교 및 검증
        assertEquals(expected, article); // 비교
    }
}