package io.hhplus.speciallecture.domain.lecture

import io.hhplus.speciallecture.domain.registration.Registration
import io.hhplus.speciallecture.domain.user.User
import jakarta.persistence.*
import java.time.LocalDate
import java.util.*

@Entity
data class Lecture(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val title: String,
    val lecturer: String,
    var capacity: Int = 30, // 최대 수용 인원 30명

    @OneToMany(mappedBy = "lecture", cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    val registrations: MutableList<Registration> = mutableListOf(),

    val lectureDate: LocalDate

) {
//    @Synchronized
    fun apply(user: User): Boolean {
        if (capacity > 0 && registrations.none { it.user.id == user.id }) {
            registrations.add(Registration(user = user, lecture = this))
            capacity--
            return true
        }
        return false
    }
}
