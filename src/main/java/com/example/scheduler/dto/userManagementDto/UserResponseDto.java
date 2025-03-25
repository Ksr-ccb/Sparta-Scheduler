package com.example.scheduler.dto.userManagementDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserResponseDto {
    private Long userId;
    private String userName;
    private String email;
    private LocalDateTime updateDate;
}
