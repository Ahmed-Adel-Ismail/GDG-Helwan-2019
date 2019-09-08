package com.sample.app.features.login

sealed class Intents
object Initialize : Intents()
data class RequestLogin(val userName: String, val password: String) : Intents()
data class RequestRegister(val userName: String, val password: String) : Intents()



