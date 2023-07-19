package com.fastcampus.sns.model.entity;


import com.fastcampus.sns.model.AlarmArgs;
import com.fastcampus.sns.model.AlarmType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "alarm" , indexes = {
        @Index(name = "user_id_idx", columnList = "user_id")
})
@Getter
@Setter
//@TypeDef(name = "json", typeClass = JsonType.class) // DB에 JSON타입 넣기 위해 mariadb 에러나서 주석처리

@SQLDelete(sql = "UPDATE alarm SET deleted_at = NOW() where id = ?")
@Where(clause="deleted_at is Null")
public class AlarmEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 알람을 받은 사람
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Enumerated(EnumType.STRING)    // enum사용
    private AlarmType alarmType;

    // mariadb 에러나서 주석처리
    /*
    @Type(type = "json")
    @Column(columnDefinition = "json") // DB에 JSON타입 넣기 위해
    private AlarmArgs alarmArgs;
    */

    @Column(name = "registered_at")
    private Timestamp registeredAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    @PrePersist
    void registeredAt() {
        this.registeredAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }

    public static AlarmEntity of(UserEntity userEntity, AlarmType alarmType, AlarmArgs args) {
        AlarmEntity entity = new AlarmEntity();
        entity.setUser(userEntity);
        entity.setAlarmType(alarmType);
        //entity.setAlarmArgs(args); mariadb 에러나서 주석처리
        return entity;
    }

}