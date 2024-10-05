package io.hhplus.speciallecture.domain.user

import org.springframework.stereotype.Repository
import java.util.*


interface UserRepository {
    // 사용자 단건 조회
    fun findById(id: Long): Optional<User>

    // 사용자 저장
    fun save(user: User): User

    // 사용자 전체 조회
    fun findAll(): List<User>
}
