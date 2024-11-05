package org.tenten.bittakotlin.calendar.entity

import jakarta.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.tenten.bittakotlin.profile.entity.Profile
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "event_calendar")
@EntityListeners(AuditingEntityListener::class)
data class EventCalendar(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "profileId", nullable = false)
    val profile: Profile? = null,

    @Column(nullable = false)
    val title: String,

    val startDate: LocalDate? = null,

    val endDate: LocalDate? = null,

    val auditionDate: LocalDateTime? = null
)
