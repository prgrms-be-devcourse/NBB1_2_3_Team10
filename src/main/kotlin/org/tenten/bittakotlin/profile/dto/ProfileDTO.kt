package org.tenten.bittakotlin.profile.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class ProfileDTO(
    val profileId: Long? = null,
    val nickname: String? = null,
    val profileUrl: String? = null,
    val description: String? = null,
    val socialMedia: String? = null,
    val job: String? = null
)