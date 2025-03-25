package com.example.scheduler.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class User {

    @Setter
    private Long id;
    private String userName;
    private String email;

    public User(String userName, String email){
        this.userName = userName;
        this.email = email;
    }
}
