package com.example.SpringBootStudy.dto;

import com.example.SpringBootStudy.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SignupResponseDto {
    private Long id;
    private String username;

    @Builder
    public SignupResponseDto(User entity){
        this.id = entity.getId();
        this.username = entity.getUsername();
    }

    public static SignupResponseDto from(User entity){
        return new SignupResponseDto(entity);
    }
}
