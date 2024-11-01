package org.tenten.bittakotlin.jobpost.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.tenten.bittakotlin.jobpost.repository.JobPostRepository
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Service
class DayScheduler @Autowired constructor(
    private val jobPostRepository: JobPostRepository
) {

    @Scheduled(cron = "0 0 0 * * ?") // 자정마다 실행
    fun checkDday() {
        val posts = jobPostRepository.findAll()

        for (post in posts) {
            val today = LocalDate.now()
            val endDate = post.endDate
            val restDate = ChronoUnit.DAYS.between(today, endDate).toInt()

            post.restDate = restDate
            jobPostRepository.save(post)
        }
    }
}