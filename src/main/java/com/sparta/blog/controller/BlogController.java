package com.sparta.blog.controller;

import com.sparta.blog.dto.BlogRequestDto;
import com.sparta.blog.dto.BlogResponseDto;
import com.sparta.blog.entity.Blog;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BlogController {

    private final Map<Long, Blog> blogList = new HashMap<>();

    @PostMapping("/blogs")
    public BlogResponseDto createBlog(@RequestBody BlogRequestDto requestDto) {
        // RequestDto -> Entity
        Blog blog = new Blog(requestDto);


        Long maxID = blogList.size() > 0 ? Collections.max(blogList.keySet()) + 1 : 1;
        blog.setId(maxID);
        // DB저장
        blogList.put(blog.getId(), blog);

        // Entity -> ResponseDto
        BlogResponseDto blogResponseDto = new BlogResponseDto(blog);

        return blogResponseDto;
    }

    @GetMapping("/blogs") //조회 api
    public List<BlogResponseDto> getBlogs() {
        // Map To List
        List<BlogResponseDto> responseList = blogList.values().stream().map(BlogResponseDto::new).toList();

        return responseList;
    }


}