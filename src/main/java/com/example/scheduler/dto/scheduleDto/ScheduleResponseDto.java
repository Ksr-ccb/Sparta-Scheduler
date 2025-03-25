package com.example.scheduler.dto.scheduleDto;

import com.example.scheduler.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


/**
 * 스케줄에 관련된 값을 리턴하는 형식을 담은 ScheduleResponseDto 입니다.
 * Schedule 인스턴스가 있거나 다른 모두 변수값이 있으면 생성이 가능합니다.
 * userName 의 경우 schedule테이블이 아닌 user 테이블에서 불러와야 하기 때문에 별도의 세터를 준비했습니다.
 */
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
