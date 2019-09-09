@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.sample.app.features.login

import androidx.lifecycle.ViewModel
import com.sample.domain.usecases.requestLogin
import com.sample.domain.usecases.requestRegister
import com.sample.entities.AuthenticationResponse
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

/**
 * for MVI, this is considered the Model function
 *
 * if this was not for Android development, we would have this class as a function instead ,
 * but we use ViewModel because of the rotation problems in Android
 *
 * we declare the following here as well :
 *
 * Intents Channel : a stream of events to be handled
 *
 * Models Channel : a stream of View Models / Ui states that will be drawn by the UI class
 *
 * and we write the logic that handles the Intents and updates the Ui Models in the initialization
 * of this [ViewModel] (or Model function)
 *
 */
class LoginViewModels(
    val intents: Channel<Intents> = Channel(1),
    val models: Channel<Model> = Channel(1),
    val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO),
    private val loginUseCase: suspend (String?, String?) -> AuthenticationResponse = loginRequest,
    private val registerUseCase: suspend (String?, String?) -> AuthenticationResponse = registerRequest
) : ViewModel() {

    init {
        coroutineScope.launch {
            models.send(Model())
            for (intent in intents) {
                when (intent) {
                    is RequestLogin -> requestLogin(intent)
                    is RequestRegister -> requestRegister(intent)
                }
            }
        }
    }


    private suspend fun requestLogin(intent: RequestLogin) {
        models.send(Model(progress = true))
        val response = loginUseCase(intent.userName, intent.password)
        models.send(Model(response.errorMessage, false, response))
    }


    private suspend fun requestRegister(intent: RequestRegister) {
        models.send(Model(progress = true))
        val response = registerUseCase(intent.userName, intent.password)
        models.send(Model(response.errorMessage, false, response))
    }

    public override fun onCleared() {
        coroutineScope.cancel()
        intents.cancel()
        models.cancel()
        super.onCleared()
    }
}

private val loginRequest: suspend (String?, String?) -> AuthenticationResponse =
    { userName, password -> requestLogin(userName, password).blockingGet() }

private val registerRequest: suspend (String?, String?) -> AuthenticationResponse =
    { userName, password -> requestRegister(userName, password).blockingGet() }




