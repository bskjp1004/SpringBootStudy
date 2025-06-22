package com.example.SpringBootStudy.entity;

import com.example.SpringBootStudy.dto.BoardRequestDto;
import com.example.SpringBootStudy.dto.BoardResponseDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name="boards")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(length = 255, nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User user;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    protected Board() {}

    @Builder
    public Board(String title, String content, String password, User user) {
        this.title = title;
        this.content = content;
        this.password = password;
        this.user = user;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void update(String title, String content, User user){
        this.title = title;
        this.content = content;
        this.user = user;
        this.updatedAt = LocalDateTime.now();
    }

    public static Board of(BoardRequestDto dto, User user) {
        return Board.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .password(dto.getPassword())
                .user(user)
                .build();
    }

}
