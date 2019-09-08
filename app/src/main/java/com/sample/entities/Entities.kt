package com.sample.entities

import java.io.Serializable

data class UserCredentials(val userName: String, val password: String) : Serializable

data class AuthenticationResponse(
    val success: Boolean = false,
    val token: String? = null,
    val errorMessage: String? = null
) : Serializable

data class AnalyticsEvent(
    val name: String,
    val value: Any? = null,
    val properties: Map<Any, Any>? = null
)
