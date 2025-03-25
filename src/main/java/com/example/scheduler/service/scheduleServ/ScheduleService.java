package com.example.scheduler.service.scheduleServ;

import com.example.scheduler.dto.scheduleDto.ScheduleResponseDto;
import com.example.scheduler.dto.scheduleDto.ScheduleRequestDto;

import java.util.List;

public interface ScheduleService {

    ScheduleResponseDto saveSchedule(ScheduleRequestDto dto);
    List<ScheduleResponseDto> finaAllSchedules(String userName, String updateDate);

    ScheduleResponseDto findScheduleById(Long id);
//    ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto dto);
//
    void deleteSchedule(Long id, String password);
}
