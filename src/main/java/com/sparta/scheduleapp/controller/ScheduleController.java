package com.sparta.scheduleapp.controller;

import com.sparta.scheduleapp.dto.ScheduleRequestDto;
import com.sparta.scheduleapp.entity.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

//Rest API 를 통해 일정관리 기능 제공

@Controller
@RequestMapping("/api/schedules") //API 명세 참고
public class ScheduleController {

    private final JdbcTemplate jdbcTemplate; //JdbcTemplate 선언

    public ScheduleController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    } //jdbc 템플릿을 생성자 주입 방식으로 초기화

    //전체 일정 조회(GET)
    @GetMapping
    public String getSchedules(Model model) {
        String sql = "SELECT id, author, password, task, created_date, updated_date FROM schedules";

        // RowMapper를 사용하여 SQL 결과를 Schedule 객체로 변환
        List<Schedule> schedules = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Schedule schedule = new Schedule();
            schedule.setId(rs.getLong("id"));
            schedule.setAuthor(rs.getString("author"));
            schedule.setPassword(rs.getString("password"));
            schedule.setTask(rs.getString("task"));
            schedule.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
            schedule.setUpdatedDate(rs.getTimestamp("updated_date").toLocalDateTime());
            return schedule;
        });

        model.addAttribute("schedules", schedules); // 모델에 스케줄 목록 추가
        return "scheduleManager"; // scheduleManager.html 템플릿을 반환
    }

    // 일정 생성 기능 (POST, /api/schedules)
    @PostMapping
    public String createSchedule(@ModelAttribute ScheduleRequestDto scheduleRequestDto) {
        String sql = "INSERT INTO schedules (author, password, task, created_date, updated_date) VALUES (?, ?, ?, ?, ?)";
        LocalDateTime now = LocalDateTime.now(); // 현재 시간

        // 데이터베이스에 새로운 일정 추가
        jdbcTemplate.update(sql, scheduleRequestDto.getAuthor(), scheduleRequestDto.getPassword(),
                scheduleRequestDto.getTask(), now, now);

        return "redirect:/api/schedules"; // 생성 후 일정 목록으로 리다이렉트
    }

    // 선택 일정 조회 기능 (GET, /api/schedules/{id})
    @GetMapping("/{id}")
    public String getSchedule(@PathVariable Long id, Model model) {
        String sql = "SELECT id, author, password, task, created_date, updated_date FROM schedules WHERE id = ?";
        Schedule schedule = jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            Schedule sch = new Schedule();
            sch.setId(rs.getLong("id"));
            sch.setAuthor(rs.getString("author"));
            sch.setPassword(rs.getString("password"));
            sch.setTask(rs.getString("task"));
            sch.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
            sch.setUpdatedDate(rs.getTimestamp("updated_date").toLocalDateTime());
            return sch;
        });

        model.addAttribute("schedule", schedule); // 모델에 선택한 일정 추가
        return "scheduleDetail"; // 일정 상세 페이지로 이동
    }

    // 일정 수정 페이지로 이동
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        String sql = "SELECT id, author, password, task, created_date, updated_date FROM schedules WHERE id = ?";
        Schedule schedule = jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            Schedule sch = new Schedule();
            sch.setId(rs.getLong("id"));
            sch.setAuthor(rs.getString("author"));
            sch.setPassword(rs.getString("password"));
            sch.setTask(rs.getString("task"));
            sch.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
            sch.setUpdatedDate(rs.getTimestamp("updated_date").toLocalDateTime());
            return sch;
        });

        model.addAttribute("schedule", schedule); // 선택한 일정 정보를 모델에 추가
        return "scheduleDetail"; // 수정 페이지로 이동
    }

    // 일정 수정 (POST)
    @PostMapping("/edit/{id}")
    public String updateSchedule(@PathVariable Long id, @ModelAttribute ScheduleRequestDto scheduleRequestDto, RedirectAttributes redirectAttributes) {
        String sql = "UPDATE schedules SET task = ?, updated_date = ? WHERE id = ? AND password = ?";
        LocalDateTime now = LocalDateTime.now(); // 현재 시간

        // 비밀번호가 일치할 경우 일정 수정
        int rowsAffected = jdbcTemplate.update(sql, scheduleRequestDto.getTask(), now, id, scheduleRequestDto.getPassword());
        if (rowsAffected == 0) {
            // 비밀번호가 불일치할 경우, 경고 메시지를 추가하고 수정 페이지로 리디렉션
            redirectAttributes.addFlashAttribute("errorMessage", "비밀번호가 틀렸습니다. 다시 입력해 주세요."); // 오류 메시지 추가
            redirectAttributes.addFlashAttribute("schedule", scheduleRequestDto); // 현재 입력된 스케줄 데이터도 다시 추가
            return "redirect:/api/schedules/edit/" + id; // 수정 페이지로 이동
        }
        // 수정 성공 후, 성공 메시지를 추가하고 일정 목록으로 리다이렉트
        redirectAttributes.addFlashAttribute("successMessage", "일정이 성공적으로 수정되었습니다.");
        return "redirect:/api/schedules"; // 수정 후 일정 목록으로 리다이렉트
    }

    // 일정 삭제 (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSchedule(@PathVariable Long id, @RequestParam String password) {
        String sql = "DELETE FROM schedules WHERE id = ? AND password = ?";
        int rowsAffected = jdbcTemplate.update(sql, id, password);

        if (rowsAffected == 0) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("비밀번호 불일치");
        }

        return ResponseEntity.ok("일정 삭제 성공");
    }

    // 검색 기능 추가 (GET, /api/schedules/search)
    @GetMapping("/search")
    public String searchSchedules(@RequestParam("author") String author, Model model) {
        String sql = "SELECT id, author, password, task, created_date, updated_date FROM schedules WHERE author LIKE ?";
        List<Schedule> schedules = jdbcTemplate.query(sql, new Object[]{"%" + author + "%"}, (rs, rowNum) -> {
            Schedule schedule = new Schedule();
            schedule.setId(rs.getLong("id"));
            schedule.setAuthor(rs.getString("author"));
            schedule.setPassword(rs.getString("password"));
            schedule.setTask(rs.getString("task"));
            schedule.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
            schedule.setUpdatedDate(rs.getTimestamp("updated_date").toLocalDateTime());
            return schedule;
        });

        model.addAttribute("schedules", schedules);
        return "scheduleManager"; // 결과를 scheduleManager.html에 표시
    }


    // 수정일 기준 내림차순 정렬 기능 추가 (GET, /api/schedules/sort)
    @GetMapping("/sort")
    public String sortSchedulesByUpdatedDate(Model model) {
        String sql = "SELECT id, author, password, task, created_date, updated_date FROM schedules ORDER BY updated_date DESC";
        List<Schedule> schedules = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Schedule schedule = new Schedule();
            schedule.setId(rs.getLong("id"));
            schedule.setAuthor(rs.getString("author"));
            schedule.setPassword(rs.getString("password"));
            schedule.setTask(rs.getString("task"));
            schedule.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
            schedule.setUpdatedDate(rs.getTimestamp("updated_date").toLocalDateTime());
            return schedule;
        });

        model.addAttribute("schedules", schedules);
        return "scheduleManager";
    }
}

