package com.example.scheduler.service.userManagementServ;

import com.example.scheduler.dto.userManagementDto.UserResponseDto;

public interface UserManagementService {

    UserResponseDto registerUser(String userName, String email);
}
