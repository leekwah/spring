package com.cos.photogramstart.web.dto.user;

import com.cos.photogramstart.domain.user.User;
import lombok.Data;

@Data
public class UserUpdateDto {
    private String name;
    private String password;
    private String website;
    private String bio;
    private String phone;
    private String gender;

    // 조금 위험함. 코드 수정이 필요할 예정
    public User toEntity(){
        return User.builder()
                .name(name) // 이름을 기재 안했으면 문제, Validation 체크
                .password(password) // 비밀번호를 기재 안했으면 문제, Validation 체크
                .website(website)
                .bio(bio)
                .phone(phone)
                .gender(gender)
                .build();
    };
}