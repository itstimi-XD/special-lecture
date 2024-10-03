package io.hhplus.speciallecture.integration

import io.hhplus.speciallecture.domain.lecture.Lecture
import io.hhplus.speciallecture.domain.lecture.LectureRepository
import io.hhplus.speciallecture.domain.lecture.LectureService
import io.hhplus.speciallecture.domain.user.User
import io.hhplus.speciallecture.domain.user.UserRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate

@SpringBootTest
class LectureIntegrationTests {

    @Autowired
    private lateinit var lectureService: LectureService

    @Autowired
    private lateinit var lectureRepository: LectureRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @BeforeEach
    fun setUp() {
        // 테스트 전에 강의와 사용자를 미리 세팅
        lectureRepository.save(Lecture(title = "Test Lecture", lecturer = "Test Lecturer", capacity = 30, lectureDate = LocalDate.now()))
        userRepository.save(User(name = "Test User")) // 테스트용 사용자 저장
    }

    @Test
    fun `동일한 유저가 같은 특강에 5번 신청할 때 1번만 성공`() {
        // Given
        val lecture = lectureRepository.findAll().first() // 저장된 강의 조회
        val user = userRepository.findAll().first() // 저장된 유저 조회

        var successCount = 0
        var failureCount = 0

        // 동일한 사용자가 5번 신청을 시도
        repeat(5) {
            if (lectureService.applyForLecture(lecture.id, user)) {
                successCount++
            } else {
                failureCount++
            }
        }

        // Then
        assertEquals(1, successCount, "성공한 횟수는 1번이어야 함")
        assertEquals(4, failureCount, "실패한 횟수는 4번이어야 함")
    }
}
