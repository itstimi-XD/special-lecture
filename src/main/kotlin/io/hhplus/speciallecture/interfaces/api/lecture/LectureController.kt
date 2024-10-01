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

    @GetMapping
    fun getAllLectures(): ResponseEntity<List<Lecture>> {
        val lectures = lectureFacade.getAllLectures()
        return ResponseEntity.ok(lectures)
    }
}
