package com.example.chatbot.config.security.component

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class RestAuthenticationToken: AbstractAuthenticationToken {

    private val principal: Any
    private val credentials: Any

    constructor(authorities: Collection<GrantedAuthority>?, principal: Any, credentials: Any) : super(authorities) {
        this.principal = principal
        this.credentials = credentials
        this.isAuthenticated = true
    }

    constructor(principal: Any, credentials: Any) : super(null) {
        this.principal = principal
        this.credentials = credentials
        this.isAuthenticated = false
    }

    override fun getCredentials(): Any {
        return this.credentials
    }

    override fun getPrincipal(): Any {
        return this.principal
    }
}