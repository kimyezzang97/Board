package com.board.post.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
// @NoArgsConstructor // JPA 엔티티에서 주로 사용, DTO는 필요한 경우만 사용하는 게 좋음
@AllArgsConstructor
// @RequiredArgsConstructor
public class PatchPostResponse {

    private Long boardId;

    private String title;

    private String content;

    private LocalDateTime createDate;


//    public PatchPostResponse(Long boardId, String title, String content) {
//        this.boardId = boardId;
//        this.title = title;
//        this.content = content;
//    }
}
