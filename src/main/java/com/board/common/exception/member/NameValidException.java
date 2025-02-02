package com.board.common.exception.member;

import com.board.constant.StatusEnum;
import lombok.Getter;

/**
 * 규칙 valid error
 */
@Getter
public class NameValidException extends RuntimeException{
    private final StatusEnum status;

    public NameValidException() {
        this.status = StatusEnum.VALID_ERROR;
    }
}
