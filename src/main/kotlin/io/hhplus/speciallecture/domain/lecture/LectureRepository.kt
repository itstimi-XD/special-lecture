package io.hhplus.speciallecture.domain.lecture

// 강의 관련 데이터를 처리하는 인터페이스
interface LectureRepository {
    // 모든 강의 조회 기능
    fun findAll(): List<Lecture>
    // 깅의 단건 조회 기능
    fun findById(id: Long): Lecture?
    // 강의 저장 기능
    fun save(lecture: Lecture): Lecture
}
