package com.example.scheduler.dto.scheduleDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleRequestDto {

    private String thingTodo;
    private Long userId;
    private String password;
}
