package com.sparta.blog.dto;

import com.sparta.blog.entity.Blog;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BlogResponseDto {
    private Long id; //
    private String title; // 제목
    private String username; // 작성자명
    private String contents; // 작성 내용
    private Integer password; // 비밀번호
    private LocalDateTime createdAt; // 작성 될 때 시간
    private LocalDateTime modifiedAt; // 수정 될 때 시간


    public BlogResponseDto(Blog blog) {
        this.id = blog.getId();
        this.title = blog.getTitle();
        this.username = blog.getUsername();
        this.contents = blog.getContents();
        this.password = blog.getPassword();
    }

}
