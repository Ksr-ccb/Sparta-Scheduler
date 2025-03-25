package com.example.scheduler.controller;

import com.example.scheduler.dto.userManagementDto.UserRequsetDto;
import com.example.scheduler.dto.userManagementDto.UserResponseDto;
import com.example.scheduler.service.userManagementServ.UserManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserManagementController {

    private final UserManagementService userService;

    /**
     * 생성자 주입
     * 클래스가 필요로 하는 의존성을 생성자를 통해 전달하는 방식
     * @param userService @Service로 등록된 UserManagementService 구현체인 Impl
     */
    public UserManagementController(UserManagementService userService) {
        this.userService = userService;
    }

    /**
     * 새로운 일정을 만들기 위한 함수르 불러내는 컨트롤러입니다.
     * POST METHOD를 통해서 RequestBody의 Json 내용을 ScheduleRequestDto으로 변환하여 가져옵니다.
     * 새로운 일정을 만들기 위해 필요한 변수는 '내용, 비밀번호, 작성자' 세가지 입니다..
     * @param dto
     * @return
     */
    @PostMapping
    public ResponseEntity<UserResponseDto> createSchedule(
            @RequestBody UserRequsetDto dto){
        return new ResponseEntity<>(userService.registerUser(dto.getUserName(), dto.getEmail()), HttpStatus.CREATED);
    }

}
