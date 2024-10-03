package io.hhplus.speciallecture.domain.user

import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    fun getUser(userId: Long): User? {
        return userRepository.findById(userId).orElse(null)
    }
}
