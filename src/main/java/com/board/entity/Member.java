package com.board.entity;


import com.board.constant.MemberRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Comment("아이디")
    @Column(nullable = false)
    private String username;

    @Comment("비밀번호")
    @Column(nullable = false)
    private String password;

    @Comment("계정 레벨")
    @Column(name = "role")
    private String role;

    @Builder
    public Member(String username, String password, String role){
        this.username = username;
        this.password = password;
        this.role = role;
        if(role == null || role.equals("")) this.role = "ROLE_USER";
    }

}
