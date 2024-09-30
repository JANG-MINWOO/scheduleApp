package com.sparta.scheduleapp.dto;

import java.time.LocalDateTime;

public class ScheduleResponseDto {
    private Long id;
    private String task;
    private String author;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
