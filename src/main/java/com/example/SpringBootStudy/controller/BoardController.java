package com.example.SpringBootStudy.controller;

import com.example.SpringBootStudy.dto.BoardRequestDto;
import com.example.SpringBootStudy.dto.BoardResponseDto;
import com.example.SpringBootStudy.entity.User;
import com.example.SpringBootStudy.repository.UserRepository;
import com.example.SpringBootStudy.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final UserRepository userRepository;

    @GetMapping("/api/posts")
    public List<BoardResponseDto> getPosts()
    {
        return boardService.getPosts();
    }

    @PostMapping("/api/post")
    public BoardResponseDto createPost(@RequestBody BoardRequestDto requestDto, User user){
        return boardService.createPost(requestDto, user);
    }

    @GetMapping("/api/post/{id}")
    public BoardResponseDto getPost(@PathVariable Long id){
        return boardService.getPost(id);
    }

    @PutMapping("/api/post/{id}")
    public BoardResponseDto updatePost(@PathVariable Long id, @RequestBody BoardRequestDto requestDto, User user){
        return boardService.updatePost(id, requestDto, user);
    }

    @DeleteMapping("/api/post/{id}")
    public String deletePost(@PathVariable Long id, @RequestBody BoardRequestDto requestDto){
        return boardService.deletePost(id, requestDto);
    }

    // API 테스트용
//    @PostMapping("/api/post")
//    public BoardResponseDto createPost(@RequestBody BoardRequestDto requestDto){
//        User user = userRepository.findById(1L)
//                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));
//        return boardService.createPost(requestDto, user);
//    }

    // API 테스트용
//    @PutMapping("/api/post/{id}")
//    public BoardResponseDto updatePost(@PathVariable Long id, @RequestBody BoardRequestDto requestDto){
//                User user = userRepository.findById(1L)
//                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));
//        return boardService.updatePost(id, requestDto, user);
//    }

}
