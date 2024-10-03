package io.hhplus.speciallecture

import io.hhplus.speciallecture.domain.lecture.Lecture
import io.hhplus.speciallecture.domain.user.User
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate

// Lecture 엔티티의 비즈니스 로직을 검증하는 테스트
class LectureTest {

    val date = LocalDate.of(2024, 10, 4)

    @Test
    fun `수용 가능 인원이 남아있으면 신청 성공`() {
        // Given
        val user = User(name = "인수")
        val lecture = Lecture(title = "성민이의 걸스 힙합 수업", lecturer = "ANNA", capacity = 30, lectureDate = date)

        // When
        val result = lecture.apply(user)

        // Then
        assertTrue(result) // 신청 성공
        assertEquals(29, lecture.capacity) // 남은 정원 29명
    }

    @Test
    fun `수용 가능 인원이 없으면 신청 실패`() {
        // Given
        val user = User(name = "인후")
        val lecture = Lecture(title = "로쉐의 섹시 그잡채 choreography 수업", lecturer = "ROCHER", capacity = 0, lectureDate = date)

        // When
        val result = lecture.apply(user)

        // Then
        assertFalse(result) // 신청 실패
        assertEquals(0, lecture.capacity) // 남은 정원 0명
    }

    @Test
    fun `같은 사용자가 중복 신청하면 실패`() {
        // Given
        val user = User(name = "재현")
        val lecture = Lecture(title = "성민이의 카리나 UP 수업", lecturer = "ANNA", capacity = 30, lectureDate = date)

        // When
        val firstApplyResult = lecture.apply(user)
        val secondApplyResult = lecture.apply(user) // 재현이가 두 번 중복 신청

        // Then
        assertTrue(firstApplyResult) // 첫 번째 신청 성공
        assertFalse(secondApplyResult) // 두 번째 신청 실패
        assertEquals(29, lecture.capacity) // 정원은 한 번만 줄어들어 29명
    }
}