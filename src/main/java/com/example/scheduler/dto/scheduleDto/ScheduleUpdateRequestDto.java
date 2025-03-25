package com.example.scheduler.dto.scheduleDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleUpdateRequestDto {
    private String thingTodo;
    private String password;
    private String userName;

}
