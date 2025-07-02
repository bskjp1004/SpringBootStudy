package com.example.SpringBootStudy.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    @NotBlank(message = "아이디는 필수입니다.")
    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "아이디는 알파벳 소문자, 숫자를 포함해 4~10자여야 합니다.")
    private String username;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$", message = "비밀번호는 알파벳 소문자, 대문자, 숫자를 포함해 8~15자여야 합니다.")
    private String password;

    public SignupRequestDto(String username, String password){
        this.username = username;
        this.password = password;
    }
}
