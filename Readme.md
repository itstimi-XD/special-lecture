
### **Default**

- [X] 아키텍처 준수를 위한 애플리케이션 패키지 설계
- [X] 특강 도메인 테이블 설계 및 목록/신청 등 기본 기능 구현
- [X] 각 기능에 대한 **단위 테스트** 작성

### **STEP 3**

- [X] 설계한 테이블에 대한 **ERD** 및 이유를 설명하는 **README** 작성
- [X] 선착순 30명 이후의 신청자의 경우 실패하도록 개선
- [X] 동시에 동일한 특강에 대해 40명이 신청했을 때, 30명만 성공하는 것을 검증하는 **통합 테스트** 작성

---

### **애플리케이션 패키지 설계**

애플리케이션은 **클린 + 레이어드 아키텍처**를 기반으로 설계되었습니다. 이 아키텍처는 Presentation 계층에서 API 요청을 받아, 비즈니스 로직을 Application 계층의 Facade에서 처리하고, 도메인 계층에서 엔티티와 비즈니스 로직을 관리합니다. 또한 Infrastructure 계층에서는 JPA를 통해 데이터베이스와의 통신을 담당합니다.

주요 도메인은 **Lecture**와 **User**로 나누어지며, 특강 신청 및 관리에 대한 비즈니스 로직과 데이터 처리를 담당합니다.

#### **패키지 구조 설명**:

```plaintext
/src
    /interfaces (Presentation 계층)
        /api
            /lecture
                LectureController.kt  // 강의 관련 API (특강 신청, 강의 목록 조회 등)
        /dto
            LectureResponse.kt        // LectureResponse DTO 클래스
            LectureResponseConverter.kt  // Lecture 엔티티와 DTO 간의 변환 로직
    /application (Application 계층)
        /lecture
            LectureFacade.kt  // 비즈니스 유즈케이스 조합 및 처리
    /component
        /user
            UserReader.kt  // 사용자 존재 여부를 확인하는 컴포넌트
    /domain (Domain 계층)
        /lecture
            Lecture.kt  // 강의 엔티티, 수강신청 비즈니스 로직 포함
            LectureRepository.kt  // 강의 저장소 인터페이스 (비관적 락 적용)
            LectureService.kt  // 강의 비즈니스 로직 처리
        /user
            User.kt  // 사용자 엔티티
            UserRepository.kt  // 사용자 저장소 인터페이스
            UserService.kt  // 사용자 관련 비즈니스 로직
        /registration
            Registration.kt  // 사용자가 신청한 강의를 추적하는 중간 엔티티
            RegistrationRepository.kt  // Registration 엔티티를 위한 리포지토리 인터페이스
    /infrastructure (Persistence 계층)
        /lecture
            LectureRepositoryJpaImpl.kt  // 강의 저장소 JPA 구현체
        /user
            UserRepositoryJpaImpl.kt  // 사용자 저장소 JPA 구현체
        /registration
            RegistrationRepositoryJpaImpl.kt  // Registration 저장소 JPA 구현체
```

- **LectureController**는 Presentation 계층의 API 컨트롤러로서, 사용자의 요청을 받아 **LectureFacade**로 전달합니다.
- **LectureFacade**는 여러 서비스와 컴포넌트를 조합하여 비즈니스 로직을 처리하며, 트랜잭션 범위는 Service에서만 관리합니다.
- **LectureService**는 비즈니스 로직을 포함하고 있으며, **UserReader** 컴포넌트를 통해 사용자 존재 여부를 확인하고, **LectureRepository**를 통해 비관적 락을 사용한 강의 등록 로직을 처리합니다.

---

### **특강 도메인 테이블 설계 및 ERD**

애플리케이션의 핵심 도메인은 **Lecture**, **User**, **Registration** 세 가지 테이블로 구성됩니다. **Lecture**는 특강 정보, **User**는 사용자 정보, 그리고 **Registration**은 사용자가 신청한 특강을 추적하는 중간 테이블입니다.

#### **ERD (Entity Relationship Diagram)**:

- **Lecture 테이블**:
    - 강의의 기본 정보를 저장합니다.
    - 강의 수용 인원(`capacity`)이 30명으로 제한되며, 사용자가 강의에 신청할 때마다 수용 인원이 감소합니다.

- **User 테이블**:
    - 사용자의 기본 정보를 저장합니다.
    - 회원가입 및 로그인 기능은 제외되었지만, 강의 신청 시 사용자 ID가 필요합니다.

- **Registration 테이블**:
    - **Lecture**와 **User** 간의 관계를 추적하며, 사용자가 어느 특강에 신청했는지를 기록하는 중간 테이블입니다.
    - 한 번에 한 사용자는 하나의 강의에 대해서만 신청할 수 있습니다.

#### **Lecture 테이블**

| Column    | Type | Description       |
| --------- | ---- | ----------------- |
| id        | Long | Primary Key       |
| title     | String | 강의 제목          |
| lecturer  | String | 강사 이름         |
| capacity  | Int  | 남은 수용 인원      |
| lectureDate | LocalDate | 강의 날짜  |

#### **User 테이블**

| Column | Type  | Description |
| ------ | ----- | ----------- |
| id     | Long  | Primary Key |
| name   | String| 사용자 이름 |

#### **Registration 테이블**

| Column     | Type  | Description         |
| ---------- | ----- | ------------------- |
| id         | Long  | Primary Key         |
| lecture_id | Long  | 강의 ID (FK to Lecture) |
| user_id    | Long  | 사용자 ID (FK to User)   |




-----


코치님 코멘트
* PR 메세지가 명확하게 작성되어 있어서 너무 좋네요.
* Synchronized 주석처리 굳 ㅋㅋ ERD 도 적절하게 작성해주었네요
* registrations 를 OneToMany 로 한 상황에서 none 을 통해 해당 유저가 등록한 적 있는지 검증하고 있는데, 현재는 식별자만 사용하여 조회쿼리가 나가지 않는 상황이라고 하더라도 O(n) 의 시간복잡도를 가지게 됩니다. 차라리, 해당 유저가 등록한 적 있는지 단건 조회를 해보는게 더 비용이 적을 수도 있어요.
* isApplied 값을 받아서 반환하기보다는, 예외를 발생시키도록 하고 롤백으로 유도시키는게 좋은 접근일 것 같아요.
lecture.apply(user) // 여기서 예외 발생하면 하단 로직 미실행 + save 로직은 Transactional 에 의해 변경감지 되므로 필요 없음 ( 자동으로 업데이트 쿼리가 생성됨 )
만약 예외가 적절히 핸들링되지 않는다면, 비즈니스 로직상으로 오작동을 만들수도 있을 것 같아요.
* 비관적락 중 PESSIMISTIC_WRITE 의 경우, 읽기 또한 blocking 하게 됩니다. 이를 위해, lock 을 활용하는 경우 이를 명시적으로 알 수 있는 네이밍이 좋아보이며 기본 jpa 함수와 별도로 관리하는 게 좋아보입니다.
* 동시성 테스트랑 작성해뒀는데, README 수정안됨ㅋ ㅋ


