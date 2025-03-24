package com.example.scheduler.dto;

import com.example.scheduler.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {

    private Long scheduleId;
    private String thingTodo;

    private String userName;

    private String updateDate;
    private String createDate;

    public ScheduleResponseDto(Schedule schedule){
        this.scheduleId = schedule.getScheduleId();
        this.thingTodo = schedule.getThingTodo();
        this.userName = schedule.getUserName();

        this.updateDate = schedule.getUpdateDate();
        this.createDate = schedule.getCreateDate();
    }
}
