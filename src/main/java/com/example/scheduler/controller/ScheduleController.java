package com.example.scheduler.controller;

import com.example.scheduler.dto.ScheduleRequestDto;
import com.example.scheduler.dto.ScheduleResponseDto;
import com.example.scheduler.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * HTTP 요청이 오면 가장먼저 컨트롤러에서 받습니다.
 * ScheduleController는 url/schedules 으로 들어오는 요청을 처리합니다.
 */
@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    /**
     * 생성자 주입
     * 클래스가 필요로 하는 의존성을 생성자를 통해 전달하는 방식
     * @param scheduleService @Service로 등록된 ScheduleService 구현체인 Impl
     */
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    /**
     * 새로운 일정을 만들기 위한 함수르 불러내는 컨트롤러입니다.
     * POST METHOD를 통해서 RequestBody의 Json 내용을 ScheduleRequestDto으로 변환하여 가져옵니다.
     * 새로운 일정을 만들기 위해 필요한 변수는 '내용, 비밀번호, 작성자' 세가지 입니다..
     * @param dto
     * @return
     */
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(
            @RequestBody ScheduleRequestDto dto){
        return new ResponseEntity<>(scheduleService.saveSchedule(dto),HttpStatus.CREATED);
    }

    /**
     * 특정 조건에 맞는 데이터 모두를 불러옵니다.
     * 조건에 대한 설정 여부는 선택적입니다. ( OO / OX / XO/ XX 가능)
     * 만약 아무 조건없이 실행한다면 모든 ROWS를 불러옵니다.
     * @param userName
     * @param updateDate
     * @return
     */
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedule(
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String updateDate){

        return new ResponseEntity<>(scheduleService.finaAllSchedules(userName, updateDate), HttpStatus.OK);
    }

    /**
     * 아이디값에 맞는 스케줄 row 한줄을 불러오는 함수입니다.
     * @param scheduleId
     * @return
     */
    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(
            @PathVariable Long scheduleId){
        return new ResponseEntity<>(scheduleService.findScheduleById(scheduleId), HttpStatus.OK);
    }

    /**
     * 아이디 값에 맞는 스케줄 row의 내용을 수정하는 함수입니다.
     * 수정할 수 있는 내용은 한정적입니다. 일정의 내용(thingTodo)과 작성자명(userName)만 유효한 수정값으로 받아들입니다.
     * 수정이 정상적으로 완료되면 수정날짜가 자동으로 갱신됩니다.
     * @param scheduleId
     * @param dto
     * @return
     */
    @PutMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long scheduleId, @RequestBody ScheduleRequestDto dto){
        return new ResponseEntity<>(scheduleService.updateSchedule(scheduleId, dto), HttpStatus.OK);
    }


    /**
     * 아이디 값에 맞는 스케줄 row의 내용을 삭제하는 함수입니다.
     * 삭제를 위해서 스케줄 row에 맞는 비밀번호를 입력받아야합니다.
     * @param scheduleId
     * @param dto
     * @return
     */
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long scheduleId,
            @RequestBody ScheduleRequestDto dto){

        scheduleService.deleteSchedule(scheduleId, dto.getPassword());

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
