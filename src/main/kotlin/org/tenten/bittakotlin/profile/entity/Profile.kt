package org.tenten.bittakotlin.profile.entity


import jakarta.persistence.*
import org.tenten.bittakotlin.apply.entity.Apply
import org.tenten.bittakotlin.like.entity.Like
import org.tenten.bittakotlin.member.entity.Member
import org.tenten.bittakotlin.profile.constant.Job

//data class 로 변경
@Entity
class Profile(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    val member: Member,

    @Column(length = 20, nullable = false)
    var nickname: String,

    @Column(name = "profile_url")
    var profileUrl: String? = null,

    @Column(columnDefinition = "TEXT")
    var description: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    var job: Job? = null,

    @Column(columnDefinition = "JSON")
    var socialMedia: String? = null,

    @OneToMany(mappedBy = "profile", fetch = FetchType.EAGER, cascade = [CascadeType.REMOVE], orphanRemoval = true)
    val apply: List<Apply> = mutableListOf(),

    @OneToMany(mappedBy = "profile", fetch = FetchType.EAGER, cascade = [CascadeType.REMOVE], orphanRemoval = true)
    val like: List<Like> = mutableListOf()
)