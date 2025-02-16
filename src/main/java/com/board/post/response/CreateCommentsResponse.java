package com.board.post.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentsResponse {

    private Long commentId;

    private String content;

    private LocalDateTime createDate;
}
