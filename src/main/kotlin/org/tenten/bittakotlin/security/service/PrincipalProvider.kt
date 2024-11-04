package org.tenten.bittakotlin.security.service

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.tenten.bittakotlin.security.dto.CustomUserDetails

@Component
class PrincipalProvider {
    private fun getPrincipal(): CustomUserDetails? {
        val authentication = SecurityContextHolder.getContext().authentication

        return authentication.principal as? CustomUserDetails
    }

    fun getUsername(): String? {
        return getPrincipal()?.username
    }

    fun isAdmin(): Boolean {
        return getPrincipal()?.authorities?.any { it.authority == "ROLE_ADMIN" } ?: false
    }
}