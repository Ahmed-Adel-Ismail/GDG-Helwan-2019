@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.sample.app.features.login

import androidx.lifecycle.ViewModel
import com.sample.domain.usecases.requestLogin
import com.sample.domain.usecases.requestRegister
import com.sample.entities.AuthenticationResponse
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel


class Model(
    val intents: Channel<Intents> = Channel(1),
    val viewStates: Channel<ViewState> = Channel(1),
    val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO),
    private val loginUseCase: suspend (String?, String?) -> AuthenticationResponse = loginRequest,
    private val registerUseCase: suspend (String?, String?) -> AuthenticationResponse = registerRequest
) : ViewModel() {

    init {
        coroutineScope.launch {
            viewStates.send(ViewState())
            for (intent in intents) {
                when (intent) {
                    is RequestLogin -> requestLogin(intent)
                    is RequestRegister -> requestRegister(intent)
                }
            }
        }
    }


    private suspend fun requestLogin(intent: RequestLogin) {
        viewStates.send(ViewState(progress = true))
        val response = loginUseCase(intent.userName, intent.password)
        viewStates.send(ViewState(response.errorMessage, false, response))
    }


    private suspend fun requestRegister(intent: RequestRegister) {
        viewStates.send(ViewState(progress = true))
        val response = registerUseCase(intent.userName, intent.password)
        viewStates.send(ViewState(response.errorMessage, false, response))
    }

    public override fun onCleared() {
        coroutineScope.cancel()
        intents.cancel()
        viewStates.cancel()
        super.onCleared()
    }
}

private val loginRequest: suspend (String?, String?) -> AuthenticationResponse =
    { userName, password -> requestLogin(userName, password).blockingGet() }

private val registerRequest: suspend (String?, String?) -> AuthenticationResponse =
    { userName, password -> requestRegister(userName, password).blockingGet() }




