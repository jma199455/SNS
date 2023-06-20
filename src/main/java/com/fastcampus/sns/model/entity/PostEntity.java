package com.fastcampus.sns.model.entity;


import com.fastcampus.sns.model.UserRole;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "post")
@Getter
@Setter
@SQLDelete(sql = "UPDATE post SET deleted_at = NOW() where id = ?")
@Where(clause="deleted_at is Null")
public class PostEntity {



    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)         // 시퀀스사용
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "body", columnDefinition = "TEXT")        // TEXT타입으로 컬럼생성
    private String body;

    @ManyToOne
    @JoinColumn(name= "user_id")
    private UserEntity user;

    @Column(name = "registered_at")
    private Timestamp registerAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    @PrePersist
    void registeredAt() {
        this.registerAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }



}
