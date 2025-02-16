package com.board.post.request;

import com.board.entity.Board;
import com.board.entity.Comments;
import com.board.entity.Member;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateCommentsRequest {
    // 내용
    @NotNull
    private String content;

    public Comments to(Member member, Board board){
        return Comments.builder()
                .content(content)
                .member(member)
                .board(board)
                .build();
    }

}
