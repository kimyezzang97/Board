package com.board.post.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostResponse {

    private Long boardId;

    private String title;

    private String content;

    private LocalDateTime createDate;
}
