package com.example.scheduler.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


/**
 * 유저관리 이용시 필요한 데이터들을 담는 User 엔티티 입니다.
 * id는 sql에서 자동으로 부여하기 때문에 userName, email으로도 객체 생성이 가능합니다.
 */
@Getter
@AllArgsConstructor
public class User {

    private Long id;
    private String userName;
    private String email;

    public User(String userName, String email){
        this.userName = userName;
        this.email = email;
    }
}
