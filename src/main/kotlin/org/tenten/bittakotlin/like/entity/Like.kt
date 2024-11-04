package org.tenten.bittakotlin.like.entity

import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.tenten.bittakotlin.jobpost.entity.JobPost
import org.tenten.bittakotlin.profile.entity.Profile
import java.time.LocalDateTime

@Entity
@Table(name = "heart")
@EntityListeners(AuditingEntityListener::class)
data class Like(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    var profile: Profile? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_post_id", nullable = false)
    var jobPost: JobPost? = null,

    var likedAt: LocalDateTime? = null
)
