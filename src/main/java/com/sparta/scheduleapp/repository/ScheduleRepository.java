package com.sparta.scheduleapp.repository;

import com.sparta.scheduleapp.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ScheduleRepository {
    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 전체 일정 조회
    public List<Schedule> findAll() {
        String sql = "SELECT id, author, password, task, created_date, updated_date FROM schedules";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Schedule schedule = new Schedule();
            schedule.setId(rs.getLong("id"));
            schedule.setAuthor(rs.getString("author"));
            schedule.setPassword(rs.getString("password"));
            schedule.setTask(rs.getString("task"));
            schedule.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
            schedule.setUpdatedDate(rs.getTimestamp("updated_date").toLocalDateTime());
            return schedule;
        });
    }

    // 일정 저장
    public void save(Schedule schedule) {
        if (schedule.getId() == null) {
            String sql = "INSERT INTO schedules (author, password, task, created_date, updated_date) VALUES (?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, schedule.getAuthor(), schedule.getPassword(), schedule.getTask(),
                    schedule.getCreatedDate(), schedule.getUpdatedDate());
        } else {
            String sql = "UPDATE schedules SET author = ?, password = ?, task = ?, updated_date = ? WHERE id = ?";
            jdbcTemplate.update(sql, schedule.getAuthor(), schedule.getPassword(), schedule.getTask(),
                    schedule.getUpdatedDate(), schedule.getId());
        }
    }

    // ID로 일정 조회
    public Schedule findById(Long id) {
        String sql = "SELECT id, author, password, task, created_date, updated_date FROM schedules WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            Schedule schedule = new Schedule();
            schedule.setId(rs.getLong("id"));
            schedule.setAuthor(rs.getString("author"));
            schedule.setPassword(rs.getString("password"));
            schedule.setTask(rs.getString("task"));
            schedule.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
            schedule.setUpdatedDate(rs.getTimestamp("updated_date").toLocalDateTime());
            return schedule;
        });
    }

    // 일정 삭제
    public void delete(Schedule schedule) {
        String sql = "DELETE FROM schedules WHERE id = ?";
        jdbcTemplate.update(sql, schedule.getId());
    }

    // 저자 이름으로 검색
    public List<Schedule> findByAuthorContaining(String author) {
        String sql = "SELECT id, author, password, task, created_date, updated_date FROM schedules WHERE author LIKE ?";
        return jdbcTemplate.query(sql, new Object[]{"%" + author + "%"}, (rs, rowNum) -> {
            Schedule schedule = new Schedule();
            schedule.setId(rs.getLong("id"));
            schedule.setAuthor(rs.getString("author"));
            schedule.setPassword(rs.getString("password"));
            schedule.setTask(rs.getString("task"));
            schedule.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
            schedule.setUpdatedDate(rs.getTimestamp("updated_date").toLocalDateTime());
            return schedule;
        });
    }

    // 수정일 기준 내림차순 정렬
    public List<Schedule> findAllByOrderByUpdatedDateDesc() {
        String sql = "SELECT id, author, password, task, created_date, updated_date FROM schedules ORDER BY updated_date DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Schedule schedule = new Schedule();
            schedule.setId(rs.getLong("id"));
            schedule.setAuthor(rs.getString("author"));
            schedule.setPassword(rs.getString("password"));
            schedule.setTask(rs.getString("task"));
            schedule.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
            schedule.setUpdatedDate(rs.getTimestamp("updated_date").toLocalDateTime());
            return schedule;
        });
    }
}
