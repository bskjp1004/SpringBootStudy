package com.example.SpringBootStudy.service;

import com.example.SpringBootStudy.dto.LoginRequestDto;
import com.example.SpringBootStudy.dto.LoginResponseDto;
import com.example.SpringBootStudy.dto.SignupRequestDto;
import com.example.SpringBootStudy.dto.SignupResponseDto;
import com.example.SpringBootStudy.entity.User;
import com.example.SpringBootStudy.repository.UserRepository;
import com.example.SpringBootStudy.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtUtil jwtUtil;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository, passwordEncoder, jwtUtil);
    }

    @Test
    @DisplayName("회원가입 성공")
    void signup_success(){
        SignupRequestDto requestDto = new SignupRequestDto("test123", "Ab345678");

        // mocking
        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");
        when(userRepository.existsByUsername("test123")).thenReturn(false);

        User mockUser = User.of("test123", "encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        // when
        SignupResponseDto responseDto = userService.signup(requestDto);

        // then
        assertEquals("test123", responseDto.getUsername());
    }

    @Test
    @DisplayName("로그인 성공")
    void login_success(){
        // given
        LoginRequestDto requestDto = new LoginRequestDto("test123", "Ab345678");
        User user = User.of(requestDto.getUsername(), requestDto.getPassword()); // 평문 비번 사용

        // mocking
        when(userRepository.findByUsername("test123")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("Ab345678", "Ab345678")).thenReturn(true);
        when(jwtUtil.createToken("test123")).thenReturn("jwt.token");

        // when
        LoginResponseDto responseDto = userService.login(requestDto);

        // then
        assertEquals("test123", responseDto.getUsername());
        assertEquals("jwt.token", responseDto.getToken());
    }
}
