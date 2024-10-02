package io.hhplus.speciallecture.application.lecture

import io.hhplus.speciallecture.domain.lecture.Lecture
import io.hhplus.speciallecture.domain.lecture.LectureService
import org.springframework.stereotype.Service

@Service
class LectureFacade(
    private val lectureService: LectureService,
//    private val userService: UserService
) {

//    @Transactional
//    fun applyForLecture(lectureId: Long, userId: Long) {
//        val user = userService.getUser(userId) ?: throw IllegalArgumentException("User not found")
//        val lecture = lectureService.getLecture(lectureId) ?: throw IllegalArgumentException("Lecture not found")
//
//        val success = lecture.apply()
//        if (!success) {
//            throw IllegalStateException("Lecture is full")
//        }
//
//        lectureService.saveLecture(lecture)
//    }

    fun getAllLectures(): List<Lecture> {
        return lectureService.getAllLectures()
    // 모든 강의를 조회하고 LectureResponse로 변환하여 반환
    fun getAllLectures(): List<LectureResponse> {
        val lectures = lectureService.getAllLectures()
        return LectureResponseConverter.lecturesToResponse(lectures)
    }
}
