package io.hhplus.speciallecture.application.lecture

import io.hhplus.speciallecture.domain.lecture.Lecture
import io.hhplus.speciallecture.domain.lecture.LectureService
import io.hhplus.speciallecture.domain.user.UserService
import io.hhplus.speciallecture.interfaces.dto.ApiResponse
import io.hhplus.speciallecture.interfaces.dto.LectureResponse
import io.hhplus.speciallecture.interfaces.dto.LectureResponseConverter
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class LectureFacade(
    private val lectureService: LectureService,
    private val userService: UserService
) {

    // 특강 신청 처리 (동시성 문제 해결을 위해 비관적 락 사용)
    @Transactional
    fun applyForLecture(lectureId: Long, userId: Long): ApiResponse {
        val user = userService.getUser(userId)
            ?: throw IllegalArgumentException("User not found")

        val success = lectureService.applyForLecture(lectureId, user)

        if (!success) {
            throw IllegalStateException("Lecture is full or user already registered")
        }

        return ApiResponse(message = "Application successful")
    }

    // 모든 강의를 조회하고 LectureResponse로 변환하여 반환
    fun getAllLectures(): List<LectureResponse> {
        val lectures = lectureService.getAllLectures()
        return LectureResponseConverter.lecturesToResponse(lectures)
    }

    fun getAvailableLecturesByDate(date: LocalDate): List<LectureResponse> {
        val lectures = lectureService.getLecturesByDate(date)
        return lectures
            .filter { it.capacity > 0 }  // 정원이 0인 특강 제외
            .map { LectureResponseConverter.lectureToResponse(it) }
    }

    // 사용자가 신청한 특강 목록을 조회하고 LectureResponse로 변환하여 반환
    fun getUserRegistrations(userId: Long): List<LectureResponse> {
        val user =userService.getUser(userId)
            ?: throw IllegalArgumentException("User not found")

        return user.registrations.map { it.lecture }
            .let { LectureResponseConverter.lecturesToResponse(it) }
    }
}
