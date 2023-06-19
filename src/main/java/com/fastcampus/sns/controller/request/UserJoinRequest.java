package com.fastcampus.sns.controller.request;

import com.fastcampus.sns.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserJoinRequest {

    private String userName;
    private String password;

    // ObjectMapper가 @RequestBody를 바인딩할 때 기본 생성자를 사용하기 때문에 기본생성자를 만들어 줬어야 한다.
    public UserJoinRequest() {

    }

}
