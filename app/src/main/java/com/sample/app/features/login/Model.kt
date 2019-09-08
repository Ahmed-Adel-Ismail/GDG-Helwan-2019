@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.sample.app.features.login

import com.sample.domain.usecases.requestLogin
import com.sample.domain.usecases.requestRegister
import com.sample.entities.AuthenticationResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.supervisorScope


class Model(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val bufferCapacity: Int = 0,
    private val loginUseCase: suspend (String?, String?) -> AuthenticationResponse = loginRequest,
    private val registerUseCase: suspend (String?, String?) -> AuthenticationResponse = registerRequest
) {

    suspend operator fun invoke(intents: Channel<Intents>) = supervisorScope {
        produce<ViewState>(dispatcher, bufferCapacity) {
            when (val intent = intents.receive()) {
                is Initialize -> send(ViewState())
                is RequestLogin -> requestLogin(intent)
                is RequestRegister -> requestRegister(intent)
            }
        }
    }

    private suspend fun ProducerScope<ViewState>.requestLogin(intent: RequestLogin) {
        send(ViewState(progress = true))
        val response = loginUseCase(intent.userName, intent.password)
        send(ViewState(response.errorMessage, false, response))
    }


    private suspend fun ProducerScope<ViewState>.requestRegister(intent: RequestRegister) {
        send(ViewState(progress = true))
        val response = registerUseCase(intent.userName, intent.password)
        send(ViewState(response.errorMessage, false, response))
    }

}

private val loginRequest: suspend (String?, String?) -> AuthenticationResponse =
    { userName, password -> requestLogin(userName, password).blockingGet() }

private val registerRequest: suspend (String?, String?) -> AuthenticationResponse =
    { userName, password -> requestRegister(userName, password).blockingGet() }




