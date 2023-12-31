package com.sparta.blog.controller;

import com.sparta.blog.dto.BlogRequestDto;
import com.sparta.blog.dto.BlogResponseDto;
import com.sparta.blog.service.BlogService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BlogController {

    private final JdbcTemplate jdbcTemplate;

    public BlogController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/blogs")
    public BlogResponseDto createBlog(@RequestBody BlogRequestDto requestDto) {
        BlogService blogService = new BlogService(jdbcTemplate);
        return blogService.createBlog(requestDto);
    }

    @GetMapping("/blogs")
    public List<BlogResponseDto> getBlogs() {
        BlogService blogService = new BlogService(jdbcTemplate);
        return blogService.getBlogs();
    }

    @PutMapping("/blogs/{id}")
    public Long updateBlog(@PathVariable Long id, @RequestBody BlogRequestDto requestDto) {
        BlogService blogService = new BlogService(jdbcTemplate);
        return blogService.updateBlog(id, requestDto);
    }

    @DeleteMapping("/blogs/{id}")
    public Long deleteBlog(@PathVariable Long id) {
        BlogService blogService = new BlogService(jdbcTemplate);
        return blogService.deleteBlog(id);
    }
}