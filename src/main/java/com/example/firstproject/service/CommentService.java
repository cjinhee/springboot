package com.example.firstproject.service;

import com.example.firstproject.dto.CommentDTO;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository; // 댓글 리파지터리 객체 주입

    @Autowired
    private ArticleRepository articleRepository; // 게시글 리파지터리 객체 주문

    public List<CommentDTO> comments(Long articleId) {
        /*
        // 1. 댓글 조회
        List<Comment> comments = commentRepository.findByArticleId(articleId);
        // 2. 엔티티 -> DTO 변환
        // dtos는 레퍼런스 변수로 댓글 DTO를 관리하는 ArrayList를 가리킴
        List<CommentDTO> dtos = new ArrayList<CommentDTO>();
        for (int i = 0; i < comments.size(); i++) { // 조회한 댓글 엔티티 수만큼 반복하기
            Comment c = comments.get(i); // 조회한 댓글 엔티티 하나씩 가져오기
            CommentDTO dto = CommentDTO.createCommentDTO(c); // 엔티티를 DTO로 변환
            dtos.add(dto); // 변환한 DTO를 dtos 리스트에 삽입
        }
        */
        // 3. 결과 반환
        return commentRepository.findByArticleId(articleId) // 댓글 엔티티 목록 조회
                .stream() // 댓글 엔티티 목록을 스트림으로 변환
                .map(comment -> CommentDTO.createCommentDTO(comment)) // 엔티티를 DTO로 매핑
                .collect(Collectors.toList()); // 스트림을 리스트로 변환
    }
    @Transactional
    public CommentDTO create(Long articleId, CommentDTO dto) {
        // 1. 게시글 조회 및 예외 발생
        Article article = articleRepository.findById(articleId) // 부모 게시글 가져오기
                .orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패!" + "대상 게시글이 없습니다.")); // 없으면 에러 메시지 출력
        // 2. 댓글 엔티티 생성
        Comment comment = Comment.createComment(dto, article);
        // 3. 댓글 엔티티를 DB에 저장
        Comment created = commentRepository.save(comment);
        // 4. DTO로 변환해 반환
        return CommentDTO.createCommentDTO(created);
    }
    @Transactional
    public CommentDTO update(Long id, CommentDTO dto) {
        // 1. 댓글 조회 및 예외 발생
        // 수정할 댓글을 가져오고 없으면 에러메시지 출력
        Comment target = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패!" + "대상 댓글이 없습니다."));
        // 2. 가져온 댓글 내용 수정하기
        target.patch(dto);
        // 3. 수정한 댓글을 DB에 갱신하기(수정 데이터로 덮어쓰기)
        Comment updated = commentRepository.save(target);
        // 4. DB에 반영된 엔티티를 DTO로 변환해 컨트롤러로 반환하기
        return CommentDTO.createCommentDTO(updated);
    }
    @Transactional
    public CommentDTO delete(Long id) {
        // 1. 댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패!" + "대상이 없습니다."));
        // 2. 댓글 삭제
        commentRepository.delete(target);
        // 3. 삭제 댓글을 DTO로 변환 및 반환
        return CommentDTO.createCommentDTO(target);
    }
}
