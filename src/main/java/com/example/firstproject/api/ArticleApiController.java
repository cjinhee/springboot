package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;

@Slf4j //로그 기능 사용
@RestController
public class ArticleApiController {
    @Autowired //게시글 리파지터리 주입
    private ArticleService articleService; // 서비스 객체 주입

    //GET
    @GetMapping("/api/articles") // URL 요청 접수
    public List<Article> index(){ //index() 메서드 정의
        return articleService.index();
    }

    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id) { //url의 id를 매개변수로 받아오기
        return articleService.show(id);
    }
    //POST
    @PostMapping("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto){
        //Article article = dto.toEntity();
        //받아온 dto는 DB에서 활용할 수 있도록 엔티티로 변환해 article 변수에 넣고, articleRepository를 통해 DB에 저장한 후 변환함
        //return articleRepository.save(article);
        Article created = articleService.create(dto); // 서비스로 게시글 생성
        return (created != null)? // 생성하면 정상, 실패하면 오류 응답
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
   //PATCH
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto){
//        // 1. DTO -> 엔티티 변환하기
//        Article article = dto.toEntity(); //dto를 엔티티로 변환
//        log.info("id: {}, article: {}", id, article.toString()); //로그 찍기
//        // 2. 타깃 조회하기
//        // DB에서 대상 엔티티를 조회해서 가져옴
//        Article target = articleRepository.findById(id).orElse(null);
//        // 3. 잘못된 요청 처리하기
//        if (target == null || id != article.getId()){ //잘못된 요청인지 판별
//            // 400, 잘못된 요청 응답!
//            log.info("잘못된 요청! id: {}, article: {}", id, article.toString()); //로그 찍기
//            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); //ResponseEntity 반환
//        }
//        // 4. 업데이트 및 정상 응답(200)하기
//        target.patch(article); // 기존 데이터에 새 데이터 붙이기
//        Article updated = articleRepository.save(target); //수정 내용 DB에 최종 저장
//        return ResponseEntity.status(HttpStatus.OK).body(updated); //정상 응답
        Article updated = articleService.update(id, dto); // 서비스를 통해 게시글 수정
        return (updated != null) ?
                ResponseEntity.status(HttpStatus.OK).body(updated) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
   }
  //DELETE
   @DeleteMapping("/api/articles/{id}")
   public ResponseEntity<Article> delete(@PathVariable Long id){
//        // 1. 대상 찾기
//        Article target = articleRepository.findById(id).orElse(null);
//        // 2. 잘못된 요청 처리하기
//        if (target == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//        // 3. 대상 삭제하기
//        articleRepository.delete(target);
//        // ResponseEntity의 build() 메서드는 HTTP 응답의 body가 없는 ResponseEntity 객체를 생성한다. build() 메서드로 생성된 객체는 body(null)의 결과와 같음
//        return ResponseEntity.status(HttpStatus.OK).build();

       Article deleted = articleService.delete(id); // 서비스를 통해 게시글 삭제
       return (deleted != null) ? // 삭제 결과에 따라 응답 처리
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @PostMapping("/api/transaction-test") //여러 게시글 생성 요청 접수
    public ResponseEntity<List<Article>> transactionTest (@RequestBody List<ArticleForm> dtos) {
        List<Article> createdList = articleService.createArticles(dtos); //서비스 호출
        return (createdList != null) ?
                ResponseEntity.status(HttpStatus.OK).body(createdList) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
