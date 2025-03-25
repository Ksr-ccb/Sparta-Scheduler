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

    /**
     * [service] 사용자 등록을 하는 함수입니다.
     * @param userName 사용자 이름을 받습니다.
     * @param email 사용자의 이메일 주소를 받습니다.
     * @return 사용자 등록이 완료되면 등록된 사용자 정보를 반환합니다.
     */
    @Override
    public UserResponseDto registerUser(String userName, String email) {
        if(userName == null || email == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There are no essential values.");
        }

        //이메일 형식 소문자, 대문자, 숫자 _.- << 이게 1글자 이상 @ 소문자, 대문자, 숫자 1자 이상이고 .으로 끝나면 안됨.
        if(!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email format.");
        }

        return userManagementRepository.registerUser(new User(userName,email));
    }
}
