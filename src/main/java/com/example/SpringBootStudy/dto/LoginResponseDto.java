package com.example.SpringBootStudy.dto;

import com.example.SpringBootStudy.entity.Board;
import com.example.SpringBootStudy.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponseDto {
    private String username;
    private String token;

    @Builder
    public LoginResponseDto(String username, String token){
        this.username = username;
        this.token = token;
    }
}
