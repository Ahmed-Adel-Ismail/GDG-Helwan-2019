package com.sample.app.features.login

import com.sample.entities.AuthenticationResponse

sealed class Intents
data class RequestLogin(val userName: String, val password: String) : Intents()
data class RequestRegister(val userName: String, val password: String) : Intents()

data class Model(
    val error: String? = null,
    val progress: Boolean = false,
    val authenticationResponse: AuthenticationResponse? = null
)



