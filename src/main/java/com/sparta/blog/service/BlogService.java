package com.sparta.blog.service;

import com.sparta.blog.dto.BlogRequestDto;
import com.sparta.blog.dto.BlogResponseDto;
import com.sparta.blog.entity.Blog;
import com.sparta.blog.repository.BlogRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

//@Service
public class BlogService { // 비즈니스 로직 담당

    private final JdbcTemplate jdbcTemplate;

    public BlogService(JdbcTemplate jdbcTemplate) { // 생성자
        this.jdbcTemplate = jdbcTemplate;
    }

    public BlogResponseDto createBlog(BlogRequestDto requestDto) {
        // RequestDto -> Entity
        Blog blog = new Blog(requestDto); // blog 객체 하나가 DB의 한개의 row

        // DB 저장
        BlogRepository blogRepository = new BlogRepository(jdbcTemplate);
        Blog saveBlog = blogRepository.save(blog);

        // Entity -> ResponseDto
        BlogResponseDto blogResponseDto = new BlogResponseDto(blog);

        return blogResponseDto;
    }

    public List<BlogResponseDto> getBlogs() {
        // DB 조회
        BlogRepository blogRepository = new BlogRepository(jdbcTemplate);
        return blogRepository.findAll();
    }

    public Long updateBlog(Long id, BlogRequestDto requestDto) {
        BlogRepository blogRepository = new BlogRepository(jdbcTemplate);
        // 해당 블로그가 DB에 존재하는지 확인
        Blog blog = blogRepository.findById(id);
        if(blog != null) {
            // blog 내용 수정
            blogRepository.update(id, requestDto);

            return id;
        } else {
            throw new IllegalArgumentException("선택한 블로그는 존재하지 않습니다.");
        }
    }

    public Long deleteBlog(Long id) {
        BlogRepository blogRepository = new BlogRepository(jdbcTemplate);
        // 해당 블로그가 DB에 존재하는지 확인
        Blog blog = blogRepository.findById(id);
        if(blog != null) {
            // memo 삭제
            blogRepository.delete(id);
            return id;
        } else {
            throw new IllegalArgumentException("선택한 블로그는 존재하지 않습니다.");
        }
    }
}
