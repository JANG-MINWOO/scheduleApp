# 일정 관리 애플리케이션

이 애플리케이션은 사용자들이 일정을 생성, 조회, 수정 및 삭제할 수 있는 기능을 제공하는 일정 관리 시스템입니다.

## 기능

- **전체 일정 조회**: 사용자는 모든 일정을 조회할 수 있습니다.
- **일정 생성**: 새로운 일정을 생성할 수 있습니다.
- **일정 상세 조회**: 특정 일정을 선택하여 상세 정보를 조회할 수 있습니다.
- **일정 수정**: 선택한 일정을 수정할 수 있습니다. 비밀번호 확인 후 수정이 진행됩니다.
- **일정 삭제**: 선택한 일정을 비밀번호 확인 후 삭제할 수 있습니다.
- **작성자명으로 일정 검색**: 작성자명을 입력하여 특정 일정을 검색할 수 있습니다.
- **수정일 기준으로 일정 정렬**: 일정을 수정일 기준으로 내림차순 정렬하여 조회할 수 있습니다.

## 기술 스택

- Java
- Spring Framework
- Spring MVC
- Thymeleaf (템플릿 엔진)
- MySQL (데이터베이스)

## 설치 및 실행 방법
1. **프로젝트 클론:**
   ```bash
   git clone https://github.com/JANG-MINWOO/scheduleApp.git
   cd scheduleApp
   ```
   
2. **의존성 설치:** Maven 또는 Gradle을 사용하여 프로젝트의 의존성을 설치합니다. Maven을 사용하는 경우, 다음 명령어를 실행합니다:
   ```bash
   mvn install
   ```
   
3. **데이터베이스 설정:** MySQL 데이터베이스를 생성하고, 애플리케이션의 **application.properties** 파일에서 데이터베이스 연결 정보를 설정합니다.
4. **애플리케이션 실행:** Spring Boot 애플리케이션을 실행합니다.
5. **웹 브라우저에서 접근:** 웹 브라우저를 열고 http://localhost:8080/api/schedules 에 접속하여 애플리케이션을 사용합니다.

## 사용법
1. 일정 조회: 메인 페이지에서 모든 일정을 조회할 수 있습니다.
2. 일정 추가: 페이지 상단의 "일정 추가" 버튼을 클릭하여 새로운 일정을 추가합니다.
3. 일정 수정: 수정할 일정을 클릭하여 상세 페이지로 이동한 후, 수정 버튼을 클릭합니다. 비밀번호를 입력하여 수정합니다.
4. 일정 삭제: 삭제할 일정을 클릭하여 상세 페이지로 이동한 후, 삭제 버튼을 클릭합니다. 비밀번호를 입력하여 삭제합니다.
5. 작성자명으로 일정 검색: 메인 페이지에서 작성자명을 입력하여 일정을 검색합니다.
6. 수정일 기준 정렬: 메인 페이지에서 "수정일 기준 정렬" 버튼을 클릭하여 일정을 정렬합니다.

---
# Scheduling Application API 명세서

## 1. 전체 일정 조회

- **HTTP Method:** `GET`
- **URL:** `/api/schedules`
- **설명:** 전체 일정 목록을 조회합니다.
- **Request Body:** 없음
- **Response Body:**

  ```json
  [
    {
      "id": 1,
      "author": "장민우",
      "task": "Task 1",
      "createdDate": "2024-10-02T12:00:00",
      "updatedDate": "2024-10-02T14:00:00"
    },
    {
      "id": 2,
      "author": "민우장",
      "task": "Task 2",
      "createdDate": "2024-10-01T10:00:00",
      "updatedDate": "2024-10-01T12:00:00"
    }
  ]
  ```
  
## 2. 일정 생성

- **HTTP Method:** `POST`
- **URL:** `/api/schedules`
- **설명:** 새로운 일정을 생성합니다.
- **Request Body:** 
  ```json
  {
  "author": "장민우",
  "password": "password123",
  "task": "Task 1"
  }
  ```
  
- **Response Body:** 없음

## 3. 선택 일정 조회

- **HTTP Method:** `GET`
- **URL:** `/api/schedules/{id}`
- **설명:** 특정 ID에 해당하는 일정을 조회합니다.
- **Request Body:** 없음
- **Response Body:** 
  ```json
  {
  "id": 1,
  "author": "장민우",
  "task": "Task 1",
  "createdDate": "2024-10-02T12:00:00",
  "updatedDate": "2024-10-02T14:00:00"
  }
  ```

## 4. 일정 수정

- **HTTP Method:** `POST`
- **URL:** `/api/schedules/edit/id}`
- **설명:** 특정 ID의 일정을 수정합니다. 비밀번호가 맞아야 수정 가능합니다.
- **Request Body:** 
  ```json
  {
  "password": "password123",
  "task": "Updated Task"
  }
  ```

- **Response Body:**
  - 성공 시
  ```json
  {
  "message": "일정이 성공적으로 수정되었습니다."
  }
  ```

   - 실패 시
  ```json
  {
  "errorMessage": "비밀번호가 틀렸습니다."
  }  
   ```

## 5. 일정 삭제

- **HTTP Method:** `DELETE`
- **URL:** `/api/schedules/{id}`
- **설명:** 특정 ID의 일정을 삭제합니다. 비밀번호가 맞아야 삭제 가능합니다.
- **Request Body:** 
  ```json
  {
  "password": "password123"
  }  
   ```
- **Response Body:**
  - 성공시
  ```json
  {
  "message": "일정이 성공적으로 삭제되었습니다."
  }
  ```
  - 실패 시
  ```json
  {
  "errorMessage": "비밀번호가 틀렸습니다."
  }
  ```

## 6. 작성자 이름으로 검색

- **HTTP Method:** `GET`
- **URL:** `/api/schedules/search?author={author}`
- **설명:** 작성자 이름으로 일정을 검색합니다.
- **Request Body:** 없음
- **Response Body:**
  ```json
    [
      {
        "id": 1,
        "author": "장민우",
        "task": "Task 1",
        "createdDate": "2024-10-02T12:00:00",
        "updatedDate": "2024-10-02T14:00:00"
      }
    ]
  ```

## 7. 수정일 기준 내림차순 정렬

- **HTTP Method:** `GET`
- **URL:** `/api/schedules/sort`
- **설명:** 일정 목록을 수정일 기준 내림차순으로 정렬합니다.
- **Request Body:** 없음
- **Response Body:**
  ```json
  [
    {
      "id": 2,
      "author": "Jane",
      "task": "Task 2",
      "createdDate": "2023-10-01T10:00:00",
      "updatedDate": "2023-10-01T12:00:00"
    },
    {
      "id": 1,
      "author": "John",
      "task": "Task 1",
      "createdDate": "2023-10-02T12:00:00",
      "updatedDate": "2023-10-02T14:00:00"
    }
  ]
  ```