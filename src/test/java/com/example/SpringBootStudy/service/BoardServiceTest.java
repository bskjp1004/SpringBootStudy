package com.example.SpringBootStudy.service;

import com.example.SpringBootStudy.dto.*;
import com.example.SpringBootStudy.entity.*;
import com.example.SpringBootStudy.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

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
    @DisplayName("게시글 생성 성공")
    void createPost_success() {
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
    @DisplayName("선택한 게시글 조회 성공")
    void getPost_success() {
        // given
        User user = User.of("testuser", "password");
        Board board = Board.of(new com.example.SpringBootStudy.dto.BoardRequestDto("제목", "내용", "비번"), user);
        when(boardRepository.findById(1L)).thenReturn(Optional.of(board));

        // when
        BoardResponseDto result = boardService.getPost(1L);

        // then
        assertNotNull(result);
        assertEquals("제목", result.getTitle());
        assertEquals("내용", result.getContent());
        assertEquals("testuser", result.getUsername());
    }

    @Test
    @DisplayName("선택한 게시글 수정 성공")
    void updatePost_success(){
        // given
        User user = User.of("testuser", "password");
        Board board = Board.of(new com.example.SpringBootStudy.dto.BoardRequestDto("제목", "내용", "1234"), user);
        when(boardRepository.findById(1L)).thenReturn(Optional.of(board));
        BoardRequestDto requestDto = new BoardRequestDto("수정 제목", "수정 내용", "1234");

        // when
        BoardResponseDto result = boardService.updatePost(1L, requestDto, user);

        // then
        assertNotNull(result);
        assertEquals("수정 제목", result.getTitle());
        assertEquals("수정 내용", result.getContent());
    }

    @Test
    @DisplayName("선택한 게시글 삭제 성공")
    void deletePost_success(){
        User user = User.of("testuser", "password");
        Board board = Board.of(new com.example.SpringBootStudy.dto.BoardRequestDto("제목", "내용", "1234"), user);
        when(boardRepository.findById(1L)).thenReturn(Optional.of(board));
        BoardRequestDto requestDto = new BoardRequestDto("제목", "내용", "1234");

        // when
        String result = boardService.deletePost(1L, requestDto);

        // then
        assertNotNull(result);
        assertEquals("게시글 삭제 성공", result);
    }
}