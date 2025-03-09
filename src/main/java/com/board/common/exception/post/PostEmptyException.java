package com.board.common.exception.post;

import com.board.constant.StatusEnum;
import lombok.Getter;

/**
 * 게시물 미 존재
 */
@Getter
public class PostEmptyException extends RuntimeException{
    private final StatusEnum status;

    public PostEmptyException(){this.status = StatusEnum.EMPTY_POST;}
}
