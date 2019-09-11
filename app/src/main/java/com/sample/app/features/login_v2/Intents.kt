package com.sample.app.features.login_v2

import com.sample.entities.AuthenticationResponse
import kotlinx.coroutines.channels.Channel

fun LoginFragment.intents() = Channel<Intents>()

sealed class Intents
data class RequestLogin(val userName: String, val password: String) : Intents()
data class RequestRegister(val userName: String, val password: String) : Intents()

data class UiModel(
    val error: String? = null,
    val progress: Boolean = false,
    val authenticationResponse: AuthenticationResponse? = null
)