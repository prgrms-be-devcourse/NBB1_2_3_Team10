package org.tenten.bittakotlin.member.entity

import jakarta.persistence.*
import lombok.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.tenten.bittakotlin.profile.entity.Profile
import org.tenten.bittakotlin.scout.entity.ScoutRequest

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

    @OneToOne(mappedBy = "member", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var profile: Profile? = null,

    @OneToMany(mappedBy = "sender", cascade = [CascadeType.ALL], orphanRemoval = true)
    val sentScoutRequests: MutableList<ScoutRequest> = mutableListOf(),

    @OneToMany(mappedBy = "receiver", cascade = [CascadeType.ALL], orphanRemoval = true)
    val receivedScoutRequests: MutableList<ScoutRequest> = mutableListOf(),

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