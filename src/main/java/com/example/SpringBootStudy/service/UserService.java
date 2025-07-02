package com.example.SpringBootStudy.service;

import com.example.SpringBootStudy.dto.LoginRequestDto;
import com.example.SpringBootStudy.dto.LoginResponseDto;
import com.example.SpringBootStudy.dto.SignupRequestDto;
import com.example.SpringBootStudy.dto.SignupResponseDto;
import com.example.SpringBootStudy.entity.User;
import com.example.SpringBootStudy.repository.UserRepository;
import com.example.SpringBootStudy.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public SignupResponseDto signup(SignupRequestDto requestDto) {
        if (userRepository.existsByUsername(requestDto.getUsername())) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }

        User user = userRepository.save(User.of(
                requestDto.getUsername(),
                passwordEncoder.encode(requestDto.getPassword()
                )));
        return SignupResponseDto.from(user);
    }

    @Transactional(readOnly = true)
    public LoginResponseDto login(LoginRequestDto requestDto){

        User user = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtUtil.createToken(user.getUsername());
        return new LoginResponseDto(user.getUsername(), token);
    }
}
