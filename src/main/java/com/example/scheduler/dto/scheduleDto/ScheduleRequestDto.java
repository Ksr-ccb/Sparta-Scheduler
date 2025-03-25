package com.example.scheduler.dto.scheduleDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 스케줄 작성시에 필요한 데이터들을 담는 ScheduleRequestDto 입니다.
 * @AllArgsConstructor 어노테이션 활용으로 값이 모두 있으면 생성자 선언없이 생성할 수 있습니다.
 */
@Getter
@AllArgsConstructor
public class ScheduleRequestDto {

    private String thingTodo;
    private Long userId;
    private String password;
}
