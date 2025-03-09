package com.board.post.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PatchCommentsResponse {

    private Long commentsId;

    private String content;

    private LocalDateTime createDate;
}
