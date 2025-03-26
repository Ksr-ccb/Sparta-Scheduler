package com.example.scheduler.service.scheduleServ;

import com.example.scheduler.dto.scheduleDto.ScheduleResponseDto;
import com.example.scheduler.dto.scheduleDto.ScheduleRequestDto;

import java.util.List;
import java.util.Map;

public interface ScheduleService {

    ScheduleResponseDto saveSchedule(ScheduleRequestDto dto);
    List<ScheduleResponseDto> finaAllSchedules(String userName, String updateDate);
    List<ScheduleResponseDto> findAllScheduleByPages(Long pageNum, Long pageSize);
    ScheduleResponseDto findScheduleById(Long id);
    ScheduleResponseDto updateSchedule(Long id, Map<String, String> updateBody);
//
    void deleteSchedule(Long id, String password);
}
