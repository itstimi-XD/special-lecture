package io.hhplus.speciallecture.domain.user

import org.springframework.stereotype.Repository
import java.util.*


interface UserRepository {
    // 사용자 단건 조회
    fun findById(id: Long): Optional<User>
}
