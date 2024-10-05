package io.hhplus.speciallecture.infrastructure.user

import io.hhplus.speciallecture.domain.user.User
import io.hhplus.speciallecture.domain.user.UserRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class UserRepositoryJpaImpl(
    private val userJpaRepository: UserJpaRepository
) : UserRepository {

    override fun findById(id: Long): Optional<User> {
        return userJpaRepository.findById(id)
    }

    override fun save(user: User): User {
        return userJpaRepository.save(user)
    }

    override fun findAll(): List<User> {
        return userJpaRepository.findAll()
    }
}

// JPA 리포지토리 구현체
interface UserJpaRepository : JpaRepository<User, Long>
