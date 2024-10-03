package io.hhplus.speciallecture.application

import io.hhplus.speciallecture.application.lecture.LectureFacade
import io.hhplus.speciallecture.component.user.UserReader
import io.hhplus.speciallecture.domain.lecture.Lecture
import io.hhplus.speciallecture.domain.lecture.LectureService
import io.hhplus.speciallecture.domain.registration.Registration
import io.hhplus.speciallecture.domain.user.User
import io.hhplus.speciallecture.interfaces.dto.ApiResponse
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.time.LocalDate

class LectureFacadeTest {

    private val lectureService: LectureService = mock(LectureService::class.java)
    private val userReader: UserReader = mock(UserReader::class.java)
    private val lectureFacade = LectureFacade(lectureService, userReader)
    val date = LocalDate.of(2024, 10, 4)

    @Test
    fun `사용자가 신청한 특강 목록 조회 후 LectureResponse로 변환`() {
        // Given
        val userId = 1L
        val lecture1 = Lecture(id = 1L, title = "Lecture 1", lecturer = "Lecturer 1", capacity = 30, lectureDate = date)
        val lecture2 = Lecture(id = 2L, title = "Lecture 2", lecturer = "Lecturer 2", capacity = 30, lectureDate = date)

        val registration1 = Registration(user = User(id = userId, name = "Test User"), lecture = lecture1)
        val registration2 = Registration(user = User(id = userId, name = "Test User"), lecture = lecture2)

        val user = User(id = userId, name = "Test User", registrations = mutableListOf(registration1, registration2))

        // Mocking UserReader to return the user
        `when`(userReader.findUserById(userId)).thenReturn(user)

        // When: LectureFacade에서 getUserRegistrations 호출
        val result = lectureFacade.getUserRegistrations(userId)

        // Then: 반환된 LectureResponse 리스트가 올바르게 변환되었는지 확인
        assertNotNull(result)
        assertEquals(2, result.size)
        assertEquals("Lecture 1", result[0].title)
        assertEquals("Lecturer 1", result[0].lecturer)
        assertEquals("Lecture 2", result[1].title)
        assertEquals("Lecturer 2", result[1].lecturer)
    }

    @Test
    fun `특강 신청 로직 테스트`() {
        // Given
        val userId = 1L
        val lectureId = 1L
        val user = User(id = userId, name = "Test User", registrations = mutableListOf())

        // Mocking UserReader to return the user
        `when`(userReader.findUserById(userId)).thenReturn(user)

        // Mock LectureService to return success
        `when`(lectureService.applyForLecture(lectureId, user)).thenReturn(true)

        // When
        val result = lectureFacade.applyForLecture(lectureId, userId)

        // Then
        assertEquals(ApiResponse(message="Application successful"), result)
    }
}
