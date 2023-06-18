package com.sparta.blog.repository;

import com.sparta.blog.dto.BlogRequestDto;
import com.sparta.blog.dto.BlogResponseDto;
import com.sparta.blog.entity.Blog;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class BlogRepository {// 3 layer arichitecture에서 DB랑 연결 담당
    private final JdbcTemplate jdbcTemplate;
    public BlogRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Blog save(Blog blog) {
        // DB 저장
        KeyHolder keyHolder = new GeneratedKeyHolder(); // 기본 키를 반환받기 위한 객체

        String sql = "INSERT INTO blog (title, username, contents, password) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update( con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);

                    preparedStatement.setString(1, blog.getTitle());
                    preparedStatement.setString(2, blog.getUsername());
                    preparedStatement.setString(3, blog.getContents());
                    preparedStatement.setString(4, blog.getPassword());

                    return preparedStatement;
                },
                keyHolder);

        // DB Insert 후 받아온 기본키 확인
        Long id = keyHolder.getKey().longValue();
        blog.setId(id);

        return blog;
    }

    public List<BlogResponseDto> findAll() {
        // DB 조회
        String sql = "SELECT * FROM blog";

        return jdbcTemplate.query(sql, new RowMapper<BlogResponseDto>() {
            @Override
            public BlogResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                // SQL 의 결과로 받아온 Memo 데이터들을 MemoResponseDto 타입으로 변환해줄 메서드
                Long id = rs.getLong("id");
                String title = rs.getString("title");
                String username = rs.getString("username");
                String contents = rs.getString("contents");
                String password = rs.getString("password");
                return new BlogResponseDto(id, title, username, contents, password);
            }
        });
    }

    public void update(Long id, BlogRequestDto requestDto) {
        String sql = "UPDATE blog SET title = ?, username = ?, contents = ? WHERE id = ?";
        jdbcTemplate.update(sql, requestDto.getTittle(), requestDto.getUsername(), requestDto.getContents(), id);
    }

    public void delete(Long id) {
        String sql = "DELETE FROM blog WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Blog findById(Long id) { // 메모 찾기 (update & delete하는 데 사용)
        // DB 조회
        String sql = "SELECT * FROM blog WHERE id = ?";

        return jdbcTemplate.query(sql, resultSet -> {
            if(resultSet.next()) {
                Blog blog = new Blog();
                blog.setTitle(resultSet.getString("title"));
                blog.setUsername(resultSet.getString("username"));
                blog.setContents(resultSet.getString("contents"));
                return blog;
            } else {
                return null;
            }
        }, id);
    }
}
