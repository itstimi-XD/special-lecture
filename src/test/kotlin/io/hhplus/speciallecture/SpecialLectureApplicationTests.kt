package io.hhplus.speciallecture

import io.hhplus.speciallecture.domain.lecture.Lecture
import io.hhplus.speciallecture.domain.lecture.LectureRepository
import io.hhplus.speciallecture.domain.lecture.LectureService
import io.hhplus.speciallecture.domain.user.User
import io.hhplus.speciallecture.domain.user.UserRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@SpringBootTest
class SpecialLectureApplicationTests {

    @Autowired
    private lateinit var lectureService: LectureService

    @Autowired
    private lateinit var lectureRepository: LectureRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    private val totalUsers = 40
    private val lectureCapacity = 30

    @BeforeEach
    fun setUp() {
        // 테스트 전에 강의와 사용자를 미리 세팅
        lectureRepository.save(Lecture(title = "Test Lecture", lecturer = "Test Lecturer", capacity = lectureCapacity, lectureDate = LocalDate.now()))
        (1..40).forEach {
            userRepository.save(User(name = "User $it"))
        }
    }

    @Test
    fun `동시에 40명이 신청할 때 30명만 성공하는지 테스트`() {
        // Given
        val lecture = lectureRepository.findAll().first() // 저장된 강의 조회
        val users = userRepository.findAll() // 저장된 사용자 40명 조회

        val threadPool: ExecutorService = Executors.newFixedThreadPool(totalUsers) // 스레드 풀 생성
        val latch = CountDownLatch(totalUsers) // 40명의 스레드가 완료될 때까지 기다림

        var successCount = 0
        var failureCount = 0

        users.forEach { user ->
            threadPool.submit {
                try {
                    // 동시 신청 시도
                    if (lectureService.applyForLecture(lecture.id, user)) {
                        successCount++
                    } else {
                        failureCount++
                    }
                } finally {
                    latch.countDown() // 작업 완료
                }
            }
        }

        latch.await() // 모든 스레드가 완료될 때까지 대기

        // Then
        assertEquals(30, successCount, "성공한 사람은 $lectureCapacity 명이여야 함")
        assertEquals(10, failureCount, "실패한 사람은 ${totalUsers - lectureCapacity}명이여야 함")
    }
}
