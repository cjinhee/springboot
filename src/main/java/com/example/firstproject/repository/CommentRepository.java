package com.example.firstproject.repository;

import com.example.firstproject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    /*
        쿼리를 메서드로 작성 가능하다!
        네이티브 쿼리 메서드 : 직접 작성한 SQL 쿼리를 리파지터리 메서드로 실행할 수 있게 해줌

        -방법
        1. @Query 어노테이션 이용
        2. orm.xml 파일 이용

        * 여기서는 특정 게시글의 모든 댓글 조회는 @Query
                 특정 닉네임의 모든 댓글 조회는 orm.xml
     */

    // 특정 게시글의 모든 댓글 조회
    @Query(value = "SELECT * FROM comment where article_id = :articleId", nativeQuery = true) // value 속성에 실행하려는 쿼리 작성
    List<Comment> findByArticleId(Long articleId);
    // 특정 닉네임의 모든 댓글 조회
    List<Comment> findByNickname(String nickname);

}
