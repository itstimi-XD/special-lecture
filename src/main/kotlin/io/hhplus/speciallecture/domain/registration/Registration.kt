package io.hhplus.speciallecture.domain.registration

import io.hhplus.speciallecture.domain.lecture.Lecture
import io.hhplus.speciallecture.domain.user.User
import jakarta.persistence.*

@Entity
data class Registration(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    val user: User,

    @ManyToOne
    val lecture: Lecture
)
