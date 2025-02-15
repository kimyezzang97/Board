package com.board.post.response;

import com.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetPostListResponse {

    private Long boardId;

    private String username;

    private String title;

    private String content;

    private LocalDateTime createDate;

    // 🔹 Entity → DTO 변환용 생성자
    public GetPostListResponse(Board board) {
        this.boardId = board.getBoardId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.username = board.getMember().getUsername();
        this.createDate = board.getCreateDate();
    }
}
