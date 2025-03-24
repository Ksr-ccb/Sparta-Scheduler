package com.example.scheduler.entity;


import lombok.Getter;
import lombok.Setter;

@Getter
public class User {

    @Setter
    private Long id;
    private String userName;
    private String email;
    private String updateDate;

    public User(String userName, String email, String updateDate){
        this.userName = userName;
        this.email = email;
        this.updateDate = updateDate;
    }
}
