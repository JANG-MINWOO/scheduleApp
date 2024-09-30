package com.sparta.scheduleapp.controller;

import com.sparta.scheduleapp.dto.ScheduleRequestDto;
import com.sparta.scheduleapp.dto.ScheduleResponseDto;
import com.sparta.scheduleapp.entity.Schedule;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

//Rest API 를 통해 일정관리 기능 제공

@Controller
@RequestMapping("/api") //API 명세 참고
public class ScheduleController {

    private final JdbcTemplate jdbcTemplate; //JdbcTemplate 선언

    public ScheduleController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    } //jdbc 템플릿을 생성자 주입 방식으로 초기화

    private final RowMapper<Schedule> scheduleRowMapper = (rs, rowNum) -> {
        Schedule schedule = new Schedule();
        schedule.setId(rs.getLong("id"));
        schedule.setTask(rs.getString("task"));
        schedule.setAuthor(rs.getString("author"));
        schedule.setPassword(rs.getString("password"));
        schedule.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
        schedule.setUpdateDate(rs.getTimestamp("update_date").toLocalDateTime());
        return schedule;
    }; // 이렇게 해서 각 결과 행을 Schedule 이라는 객체로 매핑할 수 있음

    @PostMapping("/schedules")
    public String createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        String sql = "INSERT INTO schedules (task, author, password, created_date, update_date) VALUES (?, ?, ?, ?, ?)";
        LocalDateTime now = LocalDateTime.now();
        jdbcTemplate.update(sql, requestDto.getTask(), requestDto.getAuthor(),requestDto.getPassword(), now, now);
        return "일정이 성공적으로 생성되었습니다.";
    }
}

