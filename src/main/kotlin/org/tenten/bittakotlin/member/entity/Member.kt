package org.tenten.bittakotlin.member.entity

import jakarta.persistence.*
import lombok.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener::class)
data class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, unique = true)
    var username: String = "",

    @Column(nullable = false)
    var password: String = "",

    @Column(nullable = false, unique = true)
    var nickname: String = "",

    @Column(nullable = false)
    var address: String = "",

    @Column(nullable = false)
    var role: String? = null
)