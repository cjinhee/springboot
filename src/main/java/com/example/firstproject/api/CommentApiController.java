package com.example.firstproject.api;

import com.example.firstproject.dto.CommentDTO;
import com.example.firstproject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CommentApiController {

    @Autowired
    private CommentService commentService; // 댓글 서비스 객체 주입
    // 1. 댓글 조회
    @GetMapping("/api/articles/{articleId}/comments") //댓글 조회 요청 접수
    public ResponseEntity<List<CommentDTO>> comments(@PathVariable Long articleId){
        // 서비스에 위임
        List<CommentDTO> dtos = commentService.comments(articleId);
        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(dtos); // 예외처리
    }
    // 2. 댓글 생성
    // 3. 댓글 수정
    // 4. 댓글 삭제
}
