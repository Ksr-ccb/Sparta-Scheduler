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
    private String userName;

    private String updateDate;
    private String createDate;

    public Schedule(String thingTodo, String password, String userName){
        this.thingTodo = thingTodo;
        this.password = password;
        this.userName = userName;

        this.updateDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
