package com.example.scheduler.dto.userManagementDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;


/**
 * 유저정보와 관련된 값을 리턴하는 형식을 담은 UserResponseDto 입니다.
 */
@Getter
@AllArgsConstructor
public class UserResponseDto {
    private Long userId;
    private String userName;
    private String email;
    private LocalDateTime updateDate;
}
