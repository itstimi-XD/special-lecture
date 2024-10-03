package io.hhplus.speciallecture.component.user

import io.hhplus.speciallecture.domain.user.User
import io.hhplus.speciallecture.domain.user.UserRepository
import org.springframework.stereotype.Component

@Component
class UserReader(
    private val userRepository: UserRepository
) {
    fun findUserById(userId: Long): User {
        return userRepository.findById(userId)
            .orElseThrow { throw IllegalArgumentException("User not found") }
    }
}
