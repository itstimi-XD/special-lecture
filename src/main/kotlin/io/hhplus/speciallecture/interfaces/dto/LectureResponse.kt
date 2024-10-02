package io.hhplus.speciallecture.interfaces.dto

data class LectureResponse(
    val id: Long,
    val title: String,
    val lecturer: String,
    val capacity: Int
)