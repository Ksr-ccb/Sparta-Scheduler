package com.example.scheduler.repository.scheduleRepo;

import com.example.scheduler.dto.scheduleDto.ScheduleRequestDto;
import com.example.scheduler.dto.scheduleDto.ScheduleResponseDto;
import com.example.scheduler.entity.Schedule;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository {

    ScheduleResponseDto saveSchedule(ScheduleRequestDto dto);
    List<ScheduleResponseDto> findAllSchedules(String userName, LocalDateTime updateDate);
//
//    int updateSchedule(Long id, String thingTodo, String userName);
    int deleteSchedule(Long id);
//
//    Schedule findScheduleByIdOrElseThrow(Long id);
    Schedule findScheduleById(Long id);
//
}
