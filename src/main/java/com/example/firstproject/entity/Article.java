package com.example.firstproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Getter
public class Article {

    @Id //엔티티의 대푯값 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB가 id 자동 생성/ 자동 생성 기능 추가(숫자가 자동으로 매겨짐)
    private Long id;
    @Column // title 필드 선언, DB 테이블의 title 열과 연결됨
    private String title;
    @Column // content 필드 선언, DB 테이블의 content 열과 연결됨
    private String content;


    // 수정할 내용이 있는 ㄱㅇ우에만 동작.
    public void patch(Article article) {
        if (article.title != null)
            this.title = article.title;
        if (article.content != null)
            this.content = article.content;
    }
}
