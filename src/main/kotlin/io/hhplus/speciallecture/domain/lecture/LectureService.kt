package io.hhplus.speciallecture.domain.lecture

import org.springframework.stereotype.Service

@Service
class LectureService(
    private val lectureRepository: LectureRepository
) {
    fun getLecture(id: Long): Lecture? {
        return lectureRepository.findById(id)
    }

    fun applyForLecture(lecture: Lecture): Boolean {
        return lecture.apply()
    }

    fun saveLecture(lecture: Lecture) {
        lectureRepository.save(lecture)
    }

    fun getAllLectures(): List<Lecture> {
        return lectureRepository.findAll()
    }
}