package com.sparta.scheduleapp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Schedule {
    private long id; //식별번호
    private String task; //작성된 스케쥴
    private String author; //작성자
    private String password; //등록, 수정 및 삭제 등 비밀번호
    private LocalDateTime createdDate; //작성일
    private LocalDateTime updatedDate; //수정일
}
