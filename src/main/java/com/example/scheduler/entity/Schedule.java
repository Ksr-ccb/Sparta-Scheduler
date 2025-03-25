package com.example.scheduler.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 스케줄 이용시 필요한 데이터들을 담는 Schedule 엔티티 입니다.
 * thingTodo 은 update문으로 값이 변경될 수 있기 때문에 별도의 세터를 사용합니다.
 * updateDate, createDate은 년도,월,일, 시간,분,초 모두 출력하기 위해 LocalDateTime을 사용했습니다.
 */
@Getter
@AllArgsConstructor
public class Schedule {

    private Long scheduleId;

    @Setter
    private String thingTodo;

    private String password;
    private Long userId;

    private LocalDateTime updateDate;
    private LocalDateTime createDate;
}
