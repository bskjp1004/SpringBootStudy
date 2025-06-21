package com.example.SpringBootStudy.controller;

import com.example.SpringBootStudy.dto.BoardRequestDto;
import com.example.SpringBootStudy.dto.BoardResponseDto;
import com.example.SpringBootStudy.entity.User;
import com.example.SpringBootStudy.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

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
}
