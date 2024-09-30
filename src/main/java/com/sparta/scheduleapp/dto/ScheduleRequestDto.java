package com.sparta.scheduleapp.dto;

import lombok.Getter;

//클라이언트가 서버에 요청할 때 사용하는 DTO

@Getter
public class ScheduleRequestDto {
    private String task;
    private String author;
    private String password;
}
