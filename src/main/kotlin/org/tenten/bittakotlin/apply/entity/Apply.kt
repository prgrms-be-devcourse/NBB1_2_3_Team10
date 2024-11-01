package org.tenten.bittakotlin.apply.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.tenten.bittakotlin.jobpost.entity.JobPost
import java.time.LocalDateTime

@Entity
@Table(name = "application")
@EntityListeners(AuditingEntityListener::class)
data class Apply(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_post_id", nullable = false)
    var jobPost: JobPost? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    var profile: Profile? = null,

    @CreatedDate
    var appliedAt: LocalDateTime? = null,

    @Column(length = 200)
    var status: ApplyStatus? = null
)


