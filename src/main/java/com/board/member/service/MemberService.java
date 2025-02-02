package com.board.member.service;

import com.board.common.exception.member.ConflictMemberException;
import com.board.constant.MemberRole;
import com.board.member.repository.MemberRepository;
import com.board.member.request.JoinRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public MemberService(MemberRepository memberRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.memberRepository = memberRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    // username 중복 체크
    public boolean getChkUsername(String username){
        boolean status = memberRepository.existsByUsername(username);
        if(status) throw new ConflictMemberException();
        else return true;
    }

    // 회원가입
    @Transactional
    public void createMember(JoinRequest joinRequest){
        // 중복체크
        if(memberRepository.existsByUsername(joinRequest.getUsername())){
            throw new ConflictMemberException();
        }

        // 비밀번호 암호화
        String encodePassword = bCryptPasswordEncoder.encode(joinRequest.getPassword());
        joinRequest.encPassword(encodePassword);

        // 회원정보 저장
        memberRepository.save(joinRequest.to());
    }


}
