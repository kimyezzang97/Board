package com.board.member.request;

import com.board.constant.MemberRole;
import com.board.entity.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JoinRequest {

    // username
    @NotBlank(message = "username 을 공백없이 입력해주세요.")
    @Pattern(regexp = "^[A-Za-z0-9_-]{4,10}$", message = "영어 혹은 '-','_' 으로 4~10자로 입력해주세요.")
    private String username;


    // 비밀번호
    @NotBlank(message = "비밀번호를 공백없이 입력해주세요.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&~<>])[A-Za-z\\d@$!%*#?&~<>]{8,15}$",message = "비밀번호는 영어,숫자,특수문자를" +
            " 사용하여 8~20자로 입력해주세요.")
    private String password;

    // DTO to Entity
    public Member to(){
        return Member.builder()
                .username(username)
                .password(password)
                .build();
    }

    public void encPassword(String encodePassword){
        this.password = encodePassword;
    }
}
