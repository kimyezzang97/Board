package com.board.post.controller;


import com.board.config.ApiResponse;
import com.board.constant.StatusEnum;
import com.board.post.request.CreatePostRequest;
import com.board.post.request.PatchPostRequest;
import com.board.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }


    /**
     * 게시글 조회
     * @return
     */
    @GetMapping("")
    public ResponseEntity<?> getPostList(){

        return ResponseEntity.ok(ApiResponse.builder()
                .status(StatusEnum.OK)
                .msg("")
                .data(postService.getPostList())
                .build());
    }

    /**
     * 선택 게시글 조회
     * @param boardId
     * @return
     */
    @GetMapping("/{boardId}")
    public ResponseEntity<?> getPost(@PathVariable Long boardId){

        return ResponseEntity.ok(ApiResponse.builder()
                .status(StatusEnum.OK)
                .msg("")
                .data(postService.getPost(boardId))
                .build());
    }

    /**
     * 게시글 생성
     * @param createPostRequest
     * @return
     */
    @PostMapping("")
    public ResponseEntity<?> createPost(@Valid @RequestBody CreatePostRequest createPostRequest){

        return ResponseEntity.ok(ApiResponse.builder()
                .status(StatusEnum.OK)
                .msg("")
                .data(postService.createPost(createPostRequest))
                .build());
    }

    /**
     * 게시글 수정
     * @param boardId
     * @param patchPostRequest
     * @return
     */
    @PatchMapping("/{boardId}")
    public ResponseEntity<?> patchPost(@PathVariable Long boardId, @Valid @RequestBody PatchPostRequest patchPostRequest){

        return ResponseEntity.ok(ApiResponse.builder()
                .status(StatusEnum.OK)
                .msg("")
                .data(postService.patchPost(boardId, patchPostRequest))
                .build());
    }

    /**
     * 게시글 삭제
     * @param boardId
     * @return
     */
    @DeleteMapping("/{boardId}")
    public ResponseEntity<?> deletePost(@PathVariable Long boardId){
        postService.deletePost(boardId);

        return ResponseEntity.ok(ApiResponse.builder()
                .status(StatusEnum.OK)
                .msg("게시글이 삭제 되었습니다.")
                .data("")
                .build());
    }
}
