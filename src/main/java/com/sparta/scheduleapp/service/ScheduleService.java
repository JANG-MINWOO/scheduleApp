package com.sparta.scheduleapp.service;

import com.sparta.scheduleapp.dto.ScheduleRequestDto;
import com.sparta.scheduleapp.entity.Schedule;
import com.sparta.scheduleapp.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    // 전체 일정 조회
    public List<Schedule> getSchedules() {
        return scheduleRepository.findAll(); // Repository에서 전체 일정 조회
    }

    // 일정 생성
    public void createSchedule(ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = new Schedule();
        schedule.setAuthor(scheduleRequestDto.getAuthor());
        schedule.setPassword(scheduleRequestDto.getPassword());
        schedule.setTask(scheduleRequestDto.getTask());
        LocalDateTime now = LocalDateTime.now();
        schedule.setCreatedDate(now);
        schedule.setUpdatedDate(now);
        scheduleRepository.save(schedule); // Repository에 일정 저장
    }

    // 선택 일정 조회
    public Schedule getSchedule(Long id) {
        return scheduleRepository.findById(id); // Repository에서 일정 조회
    }

    // 일정 수정
    public boolean updateSchedule(Long id, ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = scheduleRepository.findById(id); // Repository에서 일정 조회
        if (!schedule.getPassword().equals(scheduleRequestDto.getPassword())) {
            return false; // 비밀번호 불일치
        }
        schedule.setTask(scheduleRequestDto.getTask());
        schedule.setAuthor(scheduleRequestDto.getAuthor()); // 작성자 이름 업데이트
        schedule.setUpdatedDate(LocalDateTime.now());
        scheduleRepository.save(schedule); // Repository에 수정된 일정 저장
        return true; // 수정 성공
    }

    // 일정 삭제
    public boolean deleteSchedule(Long id, String password) {
        Schedule schedule = scheduleRepository.findById(id); // Repository에서 일정 조회
        if (!schedule.getPassword().equals(password)) {
            return false; // 비밀번호 불일치
        }
        scheduleRepository.delete(schedule); // Repository에서 일정 삭제
        return true; // 삭제 성공
    }

    // 저자 이름으로 검색
    public List<Schedule> searchSchedulesByAuthor(String author) {
        return scheduleRepository.findByAuthorContaining(author); // Repository에서 일정 검색
    }

    // 수정일 기준 내림차순 정렬
    public List<Schedule> sortSchedulesByUpdatedDate() {
        return scheduleRepository.findAllByOrderByUpdatedDateDesc(); // Repository에서 일정 정렬
    }
}
