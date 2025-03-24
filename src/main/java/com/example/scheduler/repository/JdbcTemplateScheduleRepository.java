package com.example.scheduler.repository;

import com.example.scheduler.dto.ScheduleResponseDto;
import com.example.scheduler.entity.Schedule;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository {
    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {
        return null;
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules(String userName, String updateDate) {
        return List.of();
    }

    @Override
    public int updateSchedule(Long id, String thingTodo, String userName) {
        return 0;
    }

    @Override
    public int deleteSchedule(Long id) {
        return 0;
    }

    @Override
    public Schedule findScheduleByIdOrElseThrow(Long id) {
        return null;
    }

    @Override
    public boolean checkPassword(Long id, String password) {
        return false;
    }

    private RowMapper<ScheduleResponseDto> scheduleDtoRowMapper(){
        return new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                //여기서 행 하나씪 읽어서 MemoResponseDto객체 만들어줌
                return new ScheduleResponseDto(

                );
            }
        };
    }
    private RowMapper<Schedule> scheduleRowMapper() {
        return new RowMapper<Schedule>() {
            @Override
            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Schedule(

                );
            }
        };
    }

}
