package io.hhplus.speciallecture.domain.lecture

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Lecture(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val title: String,
    var capacity: Int = 30 // 최대 수용 인원 30명

) {
    fun apply(): Boolean {
        if (capacity > 0) {
            capacity--
            return true
        }
        return false
    }
}
