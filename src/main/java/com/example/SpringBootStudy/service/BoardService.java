package com.example.SpringBootStudy.service;

import com.example.SpringBootStudy.dto.BoardRequestDto;
import com.example.SpringBootStudy.dto.BoardResponseDto;
import com.example.SpringBootStudy.entity.Board;
import com.example.SpringBootStudy.entity.User;
import com.example.SpringBootStudy.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional(readOnly = true)
    public List<BoardResponseDto> getPosts(){
        List<Board> boards = boardRepository.findAllByOrderByCreatedAtDesc();
        List<BoardResponseDto> boardResponseDtos = new ArrayList<>();
        for (var board : boards) {
            boardResponseDtos.add(new BoardResponseDto(board));
        }
        return boardResponseDtos;
    }

    @Transactional
    public BoardResponseDto createPost(BoardRequestDto requestDto, User user){
        Board board = boardRepository.save(Board.of(requestDto, user));
        return BoardResponseDto.from(board);
    }

    @Transactional(readOnly = true)
    public BoardResponseDto getPost(Long id){
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        return BoardResponseDto.from(board);
    }

    @Transactional
    public BoardResponseDto updatePost(Long id, BoardRequestDto requestDto, User user){
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        if (!Objects.equals(board.getPassword(), requestDto.getPassword())){
            throw  new IllegalArgumentException("게시글 비밀번호가 일치하지 않습니다.");
        }

        board.update(requestDto.getTitle(), requestDto.getContent(), user);
        return BoardResponseDto.from(board);
    }

    @Transactional
    public String deletePost(Long id, BoardRequestDto requestDto){
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        if (!Objects.equals(board.getPassword(), requestDto.getPassword())){
            throw  new IllegalArgumentException("게시글 비밀번호가 일치하지 않습니다.");
        }
        boardRepository.deleteById(id);
        return "게시글 삭제 성공";
    }
}
