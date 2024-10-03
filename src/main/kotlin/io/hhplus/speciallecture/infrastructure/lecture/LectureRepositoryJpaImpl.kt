package io.hhplus.speciallecture.infrastructure.lecture

import io.hhplus.speciallecture.domain.lecture.Lecture
import io.hhplus.speciallecture.domain.lecture.LectureRepository
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class LectureRepositoryJpaImpl(
    private val lectureJpaRepository: LectureJpaRepository
) : LectureRepository {

    override fun findAll(): List<Lecture> {
        return lectureJpaRepository.findAll()
    }

    override fun save(lecture: Lecture): Lecture {
        return lectureJpaRepository.save(lecture)
    }

    override fun findById(id: Long): Optional<Lecture> {
        // 비관적 락을 사용한 findById를 호출
        return lectureJpaRepository.findById(id)
    }
}

// JPA에서 비관적 락을 사용해 findByIdForUpdate 메서드 구현
interface LectureJpaRepository : JpaRepository<Lecture, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)// 비관적 락(Pessimistic Lock)을 설정
    override fun findById(id: Long): Optional<Lecture>
}