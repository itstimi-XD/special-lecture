package io.hhplus.speciallecture.infrastructure.lecture

import io.hhplus.speciallecture.domain.lecture.Lecture
import io.hhplus.speciallecture.domain.lecture.LectureRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
class LectureRepositoryJpaImpl(
    private val lectureJpaRepository: LectureJpaRepository
) : LectureRepository {

    override fun findAll(): List<Lecture> {
        return lectureJpaRepository.findAll()
    }

    override fun findById(id: Long): Lecture? {
        return lectureJpaRepository.findById(id).orElse(null)
    }

    override fun save(lecture: Lecture): Lecture {
        return lectureJpaRepository.save(lecture)
    }
}

interface LectureJpaRepository : JpaRepository<Lecture, Long>
