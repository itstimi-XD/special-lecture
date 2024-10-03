package io.hhplus.speciallecture.domain.lecture

import io.hhplus.speciallecture.component.user.UserReader
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class LectureService(
    private val lectureRepository: LectureRepository,
    private val userReader: UserReader // UserRepository 대신 UserReader를 사용
) {
    @Transactional
    fun applyForLecture(lectureId: Long, userId: Long): Boolean {
        // 사용자 존재 여부 검증
        val user = userReader.findUserById(userId)

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