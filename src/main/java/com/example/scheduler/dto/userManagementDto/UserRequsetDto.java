package com.example.scheduler.dto.userManagementDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 유저 등록시에 필요한 데이터들을 담는 UserRequsetDto 입니다.
 * @AllArgsConstructor 어노테이션 활용으로 값이 모두 있으면 생성자 선언없이 생성할 수 있습니다.
 */
@Getter
@AllArgsConstructor
public class UserRequsetDto {
    private String userName;
    private String email;
}
