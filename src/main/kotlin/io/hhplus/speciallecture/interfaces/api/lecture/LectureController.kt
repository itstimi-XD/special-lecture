package io.hhplus.speciallecture.interfaces.api.lecture

import io.hhplus.speciallecture.application.lecture.LectureFacade
import io.hhplus.speciallecture.domain.lecture.Lecture
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/lectures")
class LectureController(
    private val lectureFacade: LectureFacade
) {

//    @PostMapping("/{lectureId}/apply")
//    fun applyForLecture(
//        @PathVariable lectureId: Long,
//        @RequestParam userId: Long
//    ): ResponseEntity<String> {
//        return try {
//            lectureFacade.applyForLecture(lectureId, userId)
//            ResponseEntity.ok("Application successful")
//        } catch (e: Exception) {
//            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
//        }
//    }

    // 모든 강의 목록 조회 API
    @GetMapping
    fun getAllLectures(): ResponseEntity<List<LectureResponse>> {
        val lectures = lectureFacade.getAllLectures()
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
