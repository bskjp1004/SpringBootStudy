package com.example.SpringBootStudy.service;

import com.example.SpringBootStudy.dto.*;
import com.example.SpringBootStudy.entity.*;
import com.example.SpringBootStudy.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {
    @Mock
    private BoardRepository boardRepository;

    private BoardService boardService;

    @BeforeEach
    void setUp() {
        boardService = new BoardService(boardRepository);
    }

    @Test
    void createPost() {
        BoardRequestDto requestDto = new BoardRequestDto("제목", "내용", "1234");
        User user = User.of("testuser", "1234");

        Board mockBoard = Board.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .password(requestDto.getPassword())
                .user(user)
                .build();

        when(boardRepository.save(any(Board.class))).thenReturn(mockBoard);

        BoardResponseDto responseDto = boardService.createPost(requestDto, user);

        assertNotNull(responseDto);
        assertEquals("제목", responseDto.getTitle());
        assertEquals("내용", responseDto.getContent());
        assertEquals("testuser", responseDto.getUsername());

    }

    @Test
    void getPost(){
        BoardResponseDto responseDto = boardService.getPost(1L);
        assertNotNull(responseDto);
    }
}