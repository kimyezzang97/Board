package com.board.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatusEnum {
    OK("OK"),

    // validation
    BAD_REQUEST(""),

    // Member
    JOIN_CONFLICT("중복 확인을 해주세요."), // 회원가입시 계정 중복일 경우
    VALID_ERROR("규칙을 지켜주세요."), //
    MEMBER_NOT_EXIST("계정이 존재하지 않습니다."), //
    POST_FORBIDDEN("권한이 없습니다."), // 게시판 수정, 삭제

    // Post
    EMPTY_POST("게시물이 존재하지 않습니다.");
    private final String message;

}
