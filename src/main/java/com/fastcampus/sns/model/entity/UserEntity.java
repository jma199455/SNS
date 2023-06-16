package com.fastcampus.sns.model.entity;

import com.fastcampus.sns.model.UserRole;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;


@Getter
@Setter
@Entity
@Table(name = "user")
@SQLDelete(sql = "UPDATE user SET deleted_at = NOW() where id=?") // 삭제한 시간 넣기 (delete sql 날라왔을 때)
@Where(clause = "deleted_at is NUll")   // SELECT할 때 삭제가 안된 값들만 가져오기 (where절을 날릴 때 해당 부분 추가)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 시퀀스사용
    private Integer id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;

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

    // deletedAt는 클래스 위에 @SQLDelete 어노테이션으로 설정

    // Entity , dto를 혼용하여 사용하지 않기 위해 (새 UserEntity를 만들기 위해)
    public static UserEntity of(String userName, String password) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userName);
        userEntity.setPassword(password);
        return userEntity;
    }



}
