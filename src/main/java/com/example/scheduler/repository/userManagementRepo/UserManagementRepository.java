package com.example.scheduler.repository.userManagementRepo;

import com.example.scheduler.dto.userManagementDto.UserResponseDto;
import com.example.scheduler.entity.User;

public interface UserManagementRepository {

    UserResponseDto registerUser(User user);
    String getUserNameById(Long id);

}
