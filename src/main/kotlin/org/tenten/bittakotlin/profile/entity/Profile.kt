package org.tenten.bittakotlin.profile.entity

import jakarta.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.tenten.bittakotlin.member.entity.Member
import javax.print.attribute.standard.Media


@Entity
@EntityListeners(AuditingEntityListener::class)
data class Profile(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @OneToOne
    @JoinColumn(name = "member_id", nullable = false)
    var member: Member,

//    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
//    var media: Media? = null,
//
//    @OneToMany(mappedBy = "profile", fetch = FetchType.LAZY)
//    var feeds: List<Feed> = emptyList(),
//
//    @OneToMany(mappedBy = "profile", fetch = FetchType.LAZY)
//    var applies: List<Apply> = emptyList()
)