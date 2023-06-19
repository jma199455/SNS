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

    public UserJoinRequest() {

    }

}
