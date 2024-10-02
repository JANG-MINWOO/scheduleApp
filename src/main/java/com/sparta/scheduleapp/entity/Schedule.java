package com.sparta.scheduleapp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;
//일정 관리에 필요한 속성 정의
//데이터베이스와 소통할 때 필요한 클래스

@Getter
@Setter
@NoArgsConstructor
public class Schedule {
    private Long id; //식별번호
    private String task; //작성된 스케쥴
    private String author; //작성자
    private String password; //등록, 수정 및 삭제 등 비밀번호
    private LocalDateTime createdDate; //작성일
    private LocalDateTime updatedDate; //수정일
}
