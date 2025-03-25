package com.example.scheduler.service.userManagementServ;

import com.example.scheduler.dto.userManagementDto.UserResponseDto;
import com.example.scheduler.entity.User;
import com.example.scheduler.repository.userManagementRepo.UserManagementRepository;
import com.example.scheduler.service.scheduleServ.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * Annotation @Service는 명시적으로  Service Layer 라는것을 나타내며 비지니스 로직을 수행합니다.
 * ScheduleServiceImpl에서는 {@link ScheduleService} 인터페이스를 구현합니다.
 */
@Service
public class UserManagementServiceImpl implements UserManagementService{

    private final UserManagementRepository userManagementRepository;

    /**
     * 생성자 주입
     * 클래스가 필요로 하는 의존성을 생성자를 통해 전달하는 방식
     * @param userManagementRepository @Repository으로 등록된 UserManagementRepository 구현체인 Impl
     */
    public UserManagementServiceImpl(UserManagementRepository userManagementRepository){
        this.userManagementRepository = userManagementRepository;
    }

    @Override
    public UserResponseDto registerUser(String userName, String email) {
        if(userName == null || email == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There are no essential values.");
        }
        return userManagementRepository.registerUser(new User(userName,email));
    }

    @Override
    public UserResponseDto updateUserInfo(Long userId, String userName, String email) {

        return null;
    }
}
