package com.board.member.controller;

import com.board.common.jwt.JWTUtil;
import com.board.member.repository.RefreshRepository;
import com.board.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/member")
public class MemberController {
    private MemberService memberService;
    private RefreshRepository refreshRepository;

    @Autowired
    public MemberController(MemberService memberService, RefreshRepository refreshRepository){
        this.memberService = memberService;
        this.refreshRepository = refreshRepository;
    }


}
