package io.hhplus.speciallecture.interfaces.dto

import io.hhplus.speciallecture.domain.lecture.Lecture

// LectureResponse로 변환하는 Converter 클래스
object LectureResponseConverter {

    // Lecture 엔티티를 LectureResponse로 변환하는 함수
    fun lectureToResponse(lecture: Lecture): LectureResponse {
        return LectureResponse(
            id = lecture.id,
            title = lecture.title,
            lecturer = lecture.lecturer,
            capacity = lecture.capacity
        )
    }

    // Lecture 엔티티 리스트를 LectureResponse 리스트로 변환하는 함수
    fun lecturesToResponse(lectures: List<Lecture>): List<LectureResponse> {
        return lectures.map { lectureToResponse(it) }
    }
}