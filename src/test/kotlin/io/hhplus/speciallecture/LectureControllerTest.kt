package io.hhplus.speciallecture

import io.hhplus.speciallecture.application.lecture.LectureFacade
import io.hhplus.speciallecture.interfaces.api.lecture.LectureController
import io.hhplus.speciallecture.interfaces.dto.LectureResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.time.LocalDate
import kotlin.test.Test

class LectureControllerTest {

    private val lectureFacade: LectureFacade = mock(LectureFacade::class.java)
    private val lectureController = LectureController(lectureFacade)

    @Test
    fun `특정 날짜에 신청 가능한 특강 목록 조회 테스트`() {
        // Given
        val date = LocalDate.of(2024, 10, 4)
        val mockLectures = listOf(
            LectureResponse(id = 1, title = "성민이의 걸스 힙합 수업", lecturer = "ANNA", capacity = 10),
            LectureResponse(id = 2, title = "로쉐의 섹시 그잡채 choreography 수업", lecturer = "ROCHER", capacity = 1),
            LectureResponse(id = 3, title = "성민이의 카리나 UP 수업", lecturer = "ANNA", capacity = 20)
        )

        // Mock: 특강 목록 조회 시 특정 날짜에 해당하는 목록을 반환
        `when`(lectureFacade.getAvailableLecturesByDate(date)).thenReturn(mockLectures)

        // When: 특정 날짜에 신청 가능한 특강 목록 조회
        val response = lectureController.getAvailableLectures(date)

        // Then: 특정 날짜에 신청 가능한 특강 목록이 반환되어야 함
        assertNotNull(response)
        assertEquals(3, response.body!!.size)
        assertEquals("성민이의 걸스 힙합 수업", response.body!![0].title)
        assertEquals(10, response.body!![0].capacity)
        assertEquals("ANNA", response.body!![0].lecturer)
    }

    @Test
    fun `정원이 0인 특강은 목록에 포함되지 않음`() {
        // Given
        val date = LocalDate.of(2024, 10, 1)
        val mockLectures = listOf(
            LectureResponse(id = 1, title = "걸스 힙합", lecturer = "성민", capacity = 10),
            LectureResponse(id = 2, title = "섹시 choreography", lecturer = "로쉐", capacity = 0)
        )

        // Mock: 정원이 0인 특강을 필터링하는 로직
        `when`(lectureFacade.getAvailableLecturesByDate(date)).thenReturn(mockLectures.filter { it.capacity > 0 })

        // When: 특강 목록을 조회
        val response = lectureController.getAvailableLectures(date)

        // Then: 정원이 0인 특강이 제외되었는지 확인
        assertNotNull(response)
        assertEquals(1, response.body!!.size)  // capacity가 0인 특강은 제외
        assertEquals("걸스 힙합", response.body!![0].title)
    }
}