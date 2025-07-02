package com.example.SpringBootStudy.controller;

import com.example.SpringBootStudy.dto.LoginRequestDto;
import com.example.SpringBootStudy.dto.LoginResponseDto;
import com.example.SpringBootStudy.dto.SignupRequestDto;
import com.example.SpringBootStudy.dto.SignupResponseDto;
import com.example.SpringBootStudy.entity.User;
import com.example.SpringBootStudy.repository.UserRepository;
import com.example.SpringBootStudy.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/api/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequestDto requestDto){
        try {
            SignupResponseDto responseDto = userService.signup(requestDto);
            return ResponseEntity.ok("회원가입 성공");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 사용 중인 아이디입니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 실패");
        }
    }

    @GetMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto requestDto){
        try {
            LoginResponseDto responseDto = userService.login(requestDto);
            return ResponseEntity.ok(responseDto);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("아이디와 비밀번호가 틀립니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("로그인 실패");
        }
    }
}
