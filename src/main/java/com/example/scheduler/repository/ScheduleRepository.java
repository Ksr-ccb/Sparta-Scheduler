package com.example.scheduler.repository;

import com.example.scheduler.dto.ScheduleResponseDto;
import com.example.scheduler.entity.Schedule;

import java.util.List;

public interface ScheduleRepository {

    //public
    ScheduleResponseDto saveSchedule(Schedule schedule);
    List<ScheduleResponseDto> findAllSchedules(String userName, String updateDate);

    int updateSchedule(Long id, String thingTodo, String userName);
    int deleteSchedule(Long id);

    Schedule findScheduleByIdOrElseThrow(Long id);

    boolean checkPassword(Long id, String password);
}
