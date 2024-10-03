package io.hhplus.speciallecture.application.lecture

import io.hhplus.speciallecture.domain.lecture.LectureService
import io.hhplus.speciallecture.domain.user.UserService
import io.hhplus.speciallecture.interfaces.dto.ApiResponse
import io.hhplus.speciallecture.interfaces.dto.LectureResponse
import io.hhplus.speciallecture.interfaces.dto.LectureResponseConverter
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class LectureFacade(
    private val lectureService: LectureService,
    private val userService: UserService
) {

    // 트랜잭션 없이 로직 실행
    fun applyForLecture(lectureId: Long, userId: Long): ApiResponse {
        // 비즈니스 로직은 LectureService에서 처리
        val success = lectureService.applyForLecture(lectureId, userId)

        return if (success) {
            ApiResponse(message = "Application successful")
        } else {
            ApiResponse(message = "Application failed")
        }
    }

    // 모든 강의를 조회하고 LectureResponse로 변환하여 반환
    fun getAllLectures(): List<LectureResponse> {
        val lectures = lectureService.getAllLectures()
        return LectureResponseConverter.lecturesToResponse(lectures)
    }

    // 특정 날짜에 진행되는 특강을 조회하고 LectureResponse로 변환하여 반환
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
