package io.hhplus.speciallecture.domain.user

import io.hhplus.speciallecture.domain.registration.Registration
import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val name: String,

    @OneToMany(mappedBy = "user")
    val registrations: MutableList<Registration> = mutableListOf()
)
