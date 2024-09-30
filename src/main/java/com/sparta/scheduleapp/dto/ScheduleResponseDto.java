package com.sparta.scheduleapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

//서버가 클라이언트에게 응답할 때 사용하는 DTO
@Setter
@Getter
public class ScheduleResponseDto {
    private Long id;
    private String task;
    private String author;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
