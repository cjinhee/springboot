package com.example.firstproject.api;

import com.example.firstproject.dto.CommentDTO;
import com.example.firstproject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<CommentDTO> create(@PathVariable Long articleId, @RequestBody CommentDTO dto){
        // 서비스에 위임 , 메서드의 반환값은 CommentDto 타입의 createdDto 라는 변수로 저장
        CommentDTO createdDto = commentService.create(articleId, dto);
        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(createdDto);
    }
    // 3. 댓글 수정
    @PatchMapping("/api/comments/{id}") // 댓글 수정 요청 접수
    public ResponseEntity<CommentDTO> update(@PathVariable Long id, @RequestBody CommentDTO dto){

        // 서비스에 위임(댓글 컨트롤러는 댓글 서비스에 수정 작업을 위임)
        // 수정 결과는 CommentDto 타입의 updatedDto라는 변수로 받음
        CommentDTO updatedDto = commentService.update(id, dto);
        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
    }
    // 4. 댓글 삭제
}
