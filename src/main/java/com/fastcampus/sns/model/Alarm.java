package com.fastcampus.sns.model;

import com.fastcampus.sns.model.entity.AlarmEntity;
import com.fastcampus.sns.model.entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.tool.schema.internal.StandardIndexExporter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class Alarm {

    private Integer id;
    private User user;
    private AlarmType alarmType;
    //private AlarmArgs alarmArgs; mariadb 에러나서 주석처리
    private Timestamp registeredAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;

    public static Alarm fromEntity(AlarmEntity entity) {
        return new Alarm(
                entity.getId(),
                User.fromEntity(entity.getUser()),
                entity.getAlarmType(),
                //entity.getAlarmArgs(), mariadb 에러나서 주석처리
                entity.getRegisteredAt(),
                entity.getUpdatedAt(),
                entity.getDeletedAt()
        );
    }

    public Alarm() {

    }


}
