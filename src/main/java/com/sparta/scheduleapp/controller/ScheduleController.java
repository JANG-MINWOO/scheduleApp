package com.sparta.scheduleapp.controller;

import com.sparta.scheduleapp.dto.ScheduleRequestDto;
import com.sparta.scheduleapp.entity.Schedule;
import com.sparta.scheduleapp.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

// Rest API를 통해 일정관리 기능 제공
@Controller
@RequestMapping("/api/schedules") // API 명세 참고
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    } // ScheduleService 를 생성자 주입 방식으로 초기화

    // 전체 일정 조회(GET)
    @GetMapping
    public String getSchedules(Model model) {
        List<Schedule> schedules = scheduleService.getSchedules(); // 서비스에서 일정 목록 조회
        model.addAttribute("schedules", schedules); // 모델에 스케줄 목록 추가
        return "scheduleManager"; // scheduleManager.html 템플릿을 반환
    }

    // 일정 생성 기능 (POST, /api/schedules)
    @PostMapping
    public String createSchedule(@ModelAttribute ScheduleRequestDto scheduleRequestDto) {
        scheduleService.createSchedule(scheduleRequestDto); // 서비스에서 일정 생성
        return "redirect:/api/schedules"; // 생성 후 일정 목록으로 리다이렉트
    }

    // 선택 일정 조회 기능 (GET, /api/schedules/{id})
    @GetMapping("/{id}")
    public String getSchedule(@PathVariable Long id, Model model) {
        Schedule schedule = scheduleService.getSchedule(id); // 서비스에서 선택한 일정 조회
        model.addAttribute("schedule", schedule); // 모델에 선택한 일정 추가
        return "scheduleDetail"; // 일정 상세 페이지로 이동
    }

    // 일정 수정 페이지로 이동
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Schedule schedule = scheduleService.getSchedule(id); // 서비스에서 선택한 일정 조회
        model.addAttribute("schedule", schedule); // 선택한 일정 정보를 모델에 추가
        return "scheduleDetail"; // 수정 페이지로 이동
    }

    // 일정 수정 (POST)
    @PostMapping("/edit/{id}")
    public String updateSchedule(@PathVariable Long id, @ModelAttribute ScheduleRequestDto scheduleRequestDto, RedirectAttributes redirectAttributes) {
        boolean isUpdated = scheduleService.updateSchedule(id, scheduleRequestDto); // 서비스에서 일정 수정
        if (!isUpdated) {
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
        boolean isDeleted = scheduleService.deleteSchedule(id, password); // 서비스에서 일정 삭제
        if (!isDeleted) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("비밀번호 불일치");
        }
        return ResponseEntity.ok("일정 삭제 성공");
    }

    // 검색 기능 추가 (GET, /api/schedules/search)
    @GetMapping("/search")
    public String searchSchedules(@RequestParam("author") String author, Model model) {
        List<Schedule> schedules = scheduleService.searchSchedulesByAuthor(author); // 서비스에서 일정 검색
        model.addAttribute("schedules", schedules); // 모델에 검색된 일정 추가
        return "scheduleManager"; // 결과를 scheduleManager.html에 표시
    }

    // 수정일 기준 내림차순 정렬 기능 추가 (GET, /api/schedules/sort)
    @GetMapping("/sort")
    public String sortSchedulesByUpdatedDate(Model model) {
        List<Schedule> schedules = scheduleService.sortSchedulesByUpdatedDate(); // 서비스에서 일정 정렬
        model.addAttribute("schedules", schedules); // 모델에 정렬된 일정 추가
        return "scheduleManager"; // 결과를 scheduleManager.html 에 표시
    }
}