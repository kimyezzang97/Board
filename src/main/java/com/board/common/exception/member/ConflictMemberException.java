package com.board.common.exception.member;

import com.board.constant.StatusEnum;
import lombok.Getter;

/**
 * 회원가입 - 중복 확인
 */
@Getter
public class ConflictMemberException extends RuntimeException{

    private final StatusEnum status;

    //  중복
    public ConflictMemberException(){
        this.status = StatusEnum.JOIN_CONFLICT;
    };
}
