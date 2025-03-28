package com.example.scheduler.repository.userManagementRepo;

import com.example.scheduler.dto.userManagementDto.UserResponseDto;
import com.example.scheduler.entity.User;

import java.util.List;

public interface UserManagementRepository {

    UserResponseDto registerUser(User user);
    String getUserNameById(Long id);
    int updateUserNameById(Long id, String userName);
    int findUsersByName(String userName);
    int checkForDuplicate (String userName, String email);
}
