package com.fastcampus.sns.controller.response;

import com.fastcampus.sns.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class AlarmResponse {

    private Integer id;
    private AlarmType alarmType;
    //private AlarmArgs alarmArgs; mariadb 에러나서 주석처리
    private String text;
    private Timestamp registeredAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;


    // PostResponse 객체로 리턴해주기 위해 (Alarm dto 를) ==> 최종리턴값 response객체
    public static AlarmResponse fromAlarm(Alarm alarm) {
        return new AlarmResponse(
                alarm.getId(),
                alarm.getAlarmType(),
                //alarm.getAlarmArgs(), mariadb 에러나서 주석처리
                alarm.getAlarmType().getAlarmText(),
                alarm.getRegisteredAt(),
                alarm.getUpdatedAt(),
                alarm.getDeletedAt()
        );
    }





}
