package com.board.post.request;

import com.board.entity.Board;
import com.board.entity.Member;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreatePostRequest {

    // 제목
    @NotNull
    private String title;

    // 내용
    @NotNull
    private String content;

    public Board to(Member member){
        return Board.builder()
                .title(title)
                .content(content)
                .member(member)
                .build();
    }

}
