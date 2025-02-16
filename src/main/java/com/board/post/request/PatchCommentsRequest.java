package com.board.post.request;

import com.board.entity.Board;
import com.board.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PatchCommentsRequest {

    private Long commentsId;

    private String content;

    public Board to(Member member){
        return Board.builder()
                .content(content)
                .member(member)
                .build();
    }

}
