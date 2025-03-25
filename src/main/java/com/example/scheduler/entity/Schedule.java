package com.example.scheduler.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
public class Schedule {

    private Long scheduleId;
    private String thingTodo;
    private String password;

    private Long userId;

    private LocalDateTime updateDate;
    private LocalDateTime createDate;

    public Schedule(
            Long scheduleId, String thingTodo, Long userId){
//            LocalDateTime updateDate, LocalDateTime createDate
        this.scheduleId = scheduleId;
        this.thingTodo = thingTodo;
        this.userId = userId;
//        this.updateDate = updateDate;
//        this.createDate = createDate;

   }
}
