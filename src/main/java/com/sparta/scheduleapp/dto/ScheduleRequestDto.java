package com.sparta.scheduleapp.dto;

import lombok.Getter;
import lombok.Setter;

//클라이언트가 서버에 요청할 때 사용하는 DTO

@Getter
@Setter
public class ScheduleRequestDto {
    private String task;
    private String author;
    private String password;
}
