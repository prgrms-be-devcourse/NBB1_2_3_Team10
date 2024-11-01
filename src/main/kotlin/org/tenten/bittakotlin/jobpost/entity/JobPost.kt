package org.tenten.bittakotlin.jobpost.entity

import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.JoinColumn
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.tenten.bittakotlin.apply.entity.Apply
import org.tenten.bittakotlin.profile.entity.Profile
import java.time.LocalDate

import jakarta.persistence.*
import lombok.*
import org.tenten.bittakotlin.like.entity.Like
import java.time.LocalDateTime

@Data
@Entity
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "job_post")
@EntityListeners(AuditingEntityListener::class)
class JobPost(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id", nullable = false)
    val member: Member, // 게시글 작성자

    @Column(length = 100, nullable = false)
    var title: String, // 게시글 제목

    @Column(length = 500, nullable = false)
    var description: String, // 설명

    @Column(length = 200, nullable = false)
    var location: String, // 촬영 지역

    @Enumerated(EnumType.STRING)
    var payStatus: PayStatus? = null, // 급여 방식

    @CreatedDate
    val createdAt: LocalDateTime? = null, // 게시글 생성일자

    @LastModifiedDate
    val updatedAt: LocalDateTime? = null, // 게시글 수정일자

    @Enumerated(EnumType.STRING)
    var workCategory: WorkCategory? = null, // 작품 카테고리

    val auditionDate: LocalDate? = null, // 오디션 일자

    val startDate: LocalDate? = null, // 촬영 기간 시작일
    val endDate: LocalDate? = null, // 촬영 기간 종료일

    val closeDate: LocalDate? = null, // 게시글 마감 일자

    var restDate: Int? = null, // 마감까지 남은 일자

    var applyCount: Int = 0, // 해당 게시글에 지원한 수

    @OneToOne(mappedBy = "jobPost", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    var media: Media? = null,

    @OneToMany(mappedBy = "jobPost", fetch = FetchType.EAGER, cascade = [CascadeType.REMOVE], orphanRemoval = true)
    val apply: MutableList<Apply> = mutableListOf(),

    @OneToMany(mappedBy = "jobPost", fetch = FetchType.EAGER, cascade = [CascadeType.REMOVE], orphanRemoval = true)
    val likes: MutableList<Like> = mutableListOf()
) {

    fun plusApplyCount() {
        this.applyCount++
    }

    fun minusApplyCount() {
        if (this.applyCount > 0) {
            this.applyCount--
        }
    }
}



