package io.hhplus.speciallecture.interfaces.api.lecture

import io.hhplus.speciallecture.application.lecture.LectureFacade
import io.hhplus.speciallecture.domain.lecture.Lecture
import io.hhplus.speciallecture.interfaces.dto.ApiResponse
import io.hhplus.speciallecture.interfaces.dto.LectureResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/lectures")
class LectureController(
    private val lectureFacade: LectureFacade
) {

    @PostMapping("/{lectureId}/apply")
    fun applyForLecture(
        @PathVariable lectureId: Long,
        @RequestParam userId: Long
    ): ResponseEntity<ApiResponse> {
        return try {
            val message = lectureFacade.applyForLecture(lectureId, userId)
            ResponseEntity.ok(message)
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(ApiResponse(e.message ?: "Error occurred"))
        }
    }

    // 모든 강의 목록 조회 API
    @GetMapping
    fun getAllLectures(): ResponseEntity<List<LectureResponse>> {
        val lectures = lectureFacade.getAllLectures()
        return ResponseEntity.ok(lectures)
    }

    @GetMapping("/available-lectures")
    fun getAvailableLectures(
        @RequestParam("date") date: LocalDate
    ): ResponseEntity<List<LectureResponse>> {
        val lectures = lectureFacade.getAvailableLecturesByDate(date)
        return ResponseEntity.ok(lectures)
    }

    @GetMapping("/users/{userId}/registrations")
    fun getUserRegistrations(
        @PathVariable userId: Long
    ): ResponseEntity<List<LectureResponse>> {
        val lectures = lectureFacade.getUserRegistrations(userId)
        return ResponseEntity.ok(lectures)
    }
}
