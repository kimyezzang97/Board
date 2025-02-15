package com.board.member.repository;

import com.board.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // username 중복 확인
    boolean existsByUsername(String username);

    Optional<Member> findByUsername(String username);




}
