
### 1. **애플리케이션 패키지 설계**

클린 + 레이어드 아키텍처에 맞게 패키지를 설계합니다. 두 개의 주요 도메인인 `Lecture`와 `User`가 있으며, 사용자 로그인/회원가입은 제외됩니다.

#### 패키지 구조

```plaintext
/src
    /interfaces (Presentation 계층)
        /api
            /lecture
                LectureController.kt  // 강의 관련 API (특강 신청, 강의 목록 조회 등)
        /dto
            LectureResponse.kt        // LectureResponse DTO 클래스
            LectureResponseConverter.kt  // 변환 로직을 여기에 정의
    /application (Application 계층)
        /lecture
            LectureFacade.kt  // 유즈케이스 조합 처리
    /domain (Domain 계층)
        /lecture
            Lecture.kt  // 강의 엔티티
            LectureRepository.kt  // 강의 저장소 인터페이스
            LectureService.kt  // 강의 비즈니스 로직 처리
        /user
            User.kt  // 사용자 엔티티
            UserRepository.kt  // 사용자 저장소 인터페이스
            UserService.kt  // 사용자 비즈니스 로직 처리
        /registration
            Registration.kt  // 특강 신청을 추적하는 중간 엔티티
            RegistrationRepository.kt  // Registration 엔티티를 위한 리포지토리 인터페이스
    /infrastructure (Persistence 계층)
        /lecture
            LectureRepositoryJpaImpl.kt  // 강의 저장소 JPA 구현체
        /user
            UserRepositoryJpaImpl.kt  // 사용자 저장소 JPA 구현체
        /registration
            RegistrationRepositoryJpaImpl.kt  // Registration 저장소 JPA 구현체
```


---

### 2. **특강 도메인 테이블 설계**
ERD (Entity Relationship Diagram)


**Lecture**

| Column | Type | Description |
| --- | --- | --- |
| id | Long | Primary Key |
| title | String | 강의 제목 |
| lecturer | String | 강사 이름 |
| capacity | Int | 남은 수용 인원 |

**User**

| Column | Type | Description |
| --- | --- | --- |
| id | Long | Primary Key |
| name | String | 사용자 이름 |


**Registration**

| Column | Type | Description |
| --- | --- | --- |
| id | Long | Primary Key |
| lecture_id | Long | 강의 ID | -- FK to Lecture
| user_id | Long | 사용자 ID | -- FK to User

---
