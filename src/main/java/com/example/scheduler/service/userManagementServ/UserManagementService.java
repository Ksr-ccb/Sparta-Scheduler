package com.example.scheduler.service.userManagementServ;

import com.example.scheduler.dto.userManagementDto.UserResponseDto;

public interface UserManagementService {

    public UserResponseDto registerUser(String userName, String email);
    public UserResponseDto updateUserInfo(Long userId, String userName, String email);
}
