package org.tenten.bittakotlin.media.entity

import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.tenten.bittakotlin.media.constant.MediaType
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
data class Media (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val filename: String,

    @Column(nullable = false)
    @ColumnDefault("0")
    val filesize: Int,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val filetype: MediaType,

    @CreatedDate
    @Column(nullable = false, updatable = false)
    val savedAt: LocalDateTime? = null,

    /*
        회원과 1:N으로 연결할 예정입니다.
        임시로 문자열로 구성해놓았습니다.
     */
    val member: String? = null
)