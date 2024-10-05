package io.hhplus.speciallecture.domain.lecture

import io.hhplus.speciallecture.domain.user.User
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class LectureService(
    private val lectureRepository: LectureRepository
) {
    @Transactional

    fun applyForLecture(lectureId: Long, user: User): Boolean {
        val lecture = lectureRepository.findById(lectureId)
            .orElseThrow { throw IllegalArgumentException("Lecture not found") }

        // 사용자가 특강 신청 로직을 처리
        val isApplied = lecture.apply(user)

        if (isApplied) {
            // 변경된 상태를 데이터베이스에 반영
            lectureRepository.save(lecture)
        }
        return isApplied
    }

    fun getAllLectures(): List<Lecture> {
        return lectureRepository.findAll()
    }

    fun getLecturesByDate(date: LocalDate): List<Lecture> {
        // 날짜별로 강의 조회하는 로직 추가
        return lectureRepository.findByDate(date)
    }
}