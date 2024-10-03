package io.hhplus.speciallecture.domain.lecture

import java.time.LocalDate
import java.util.*

interface LectureRepository {
    // 모든 강의 조회
    fun findAll(): List<Lecture>

    // 날짜별 강의 조회
    fun findByDate(date: LocalDate): List<Lecture>

    // 강의 저장
    fun save(lecture: Lecture): Lecture

    // 강의 단건 조회 (업데이트용)
    fun findById(id: Long): Optional<Lecture>
}
