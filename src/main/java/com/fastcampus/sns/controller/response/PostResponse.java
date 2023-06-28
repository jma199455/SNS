package com.fastcampus.sns.controller.response;

import com.fastcampus.sns.model.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class PostResponse {

    private Integer id;

    private String title;

    private String body;

    private  UserResponse userResponse;

    private Timestamp registeredAt;

    private Timestamp updatedAt;

    private Timestamp deletedAt;


    // PostResponse 객체로 리턴해주기 위해 (Post dto 를)
    public static PostResponse fromPost(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getBody(),
                UserResponse.fromUser(post.getUser()),
                post.getRegisteredAt(),
                post.getUpdatedAt(),
                post.getDeletedAt()
        );
    }





}
