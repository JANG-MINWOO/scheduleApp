-- 데이터베이스가 없으면 생성
CREATE DATABASE IF NOT EXISTS schedule_app;

-- 데이터베이스 선택
USE schedule_app;

-- schedules 테이블 생성
CREATE TABLE IF NOT EXISTS schedules (
    id BIGINT AUTO_INCREMENT PRIMARY KEY, -- 일정 고유 ID
    author VARCHAR(100) NOT NULL,          -- 작성자 이름
    password VARCHAR(100) NOT NULL,        -- 비밀번호
    task VARCHAR(500) NOT NULL,                     -- 일정 내용
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP, -- 생성 날짜
    updated_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- 수정 날짜
);