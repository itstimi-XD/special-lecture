package io.hhplus.speciallecture

import io.hhplus.speciallecture.domain.lecture.Lecture
import io.hhplus.speciallecture.domain.lecture.LectureRepository
import io.hhplus.speciallecture.domain.lecture.LectureService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
class LectureServiceTest {

    @Mock
    private lateinit var lectureRepository: LectureRepository

    @InjectMocks
    private lateinit var lectureService: LectureService

    @Test
    fun `모든 강의를 조회할 수 있다`() {
        // Given
        val lectures = listOf(
            Lecture(id = 1L, title = "성민이의 걸스 힙합 수업", capacity = 30),
            Lecture(id = 2L, title = "로쉐의 섹시 그잡채 choreography 수업", capacity = 0)
        )
        `when`(lectureRepository.findAll()).thenReturn(lectures)

        // When
        val result = lectureService.getAllLectures()

        // Then
        assertEquals(2, result.size)
        assertEquals("성민이의 걸스 힙합 수업", result[0].title)
        assertEquals("로쉐의 섹시 그잡채 choreography 수업", result[1].title)
    }
}