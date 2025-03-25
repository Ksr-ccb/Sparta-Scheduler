package com.example.scheduler.dto.scheduleDto;

import com.example.scheduler.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {

    private Long scheduleId;
    private String thingTodo;

    @Setter
    private String userName;

    private LocalDateTime updateDate;
    private LocalDateTime createDate;

    public ScheduleResponseDto(Schedule schedule){
        this.scheduleId = schedule.getScheduleId();
        this.thingTodo = schedule.getThingTodo();

        this.updateDate = schedule.getUpdateDate();
        this.createDate = schedule.getCreateDate();
    }
}
