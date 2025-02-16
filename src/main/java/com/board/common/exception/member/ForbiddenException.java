package com.board.common.exception.member;

import com.board.constant.StatusEnum;
import lombok.Getter;

/**
 * 게시판 수정, 삭제 - 권한 없음
 */
@Getter
public class ForbiddenException extends RuntimeException{

    private final StatusEnum status;

    //  중복
    public ForbiddenException(){
        this.status = StatusEnum.POST_FORBIDDEN;
    };

}
