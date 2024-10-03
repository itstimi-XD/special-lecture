package io.hhplus.speciallecture.domain.registration

import io.hhplus.speciallecture.domain.lecture.Lecture
import io.hhplus.speciallecture.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RegistrationRepository : JpaRepository<Registration, Long> {
    fun findByUserAndLecture(user: User, lecture: Lecture): Optional<Registration>
}
