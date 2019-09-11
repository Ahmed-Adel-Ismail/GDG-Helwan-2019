@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.sample.app.features.login_v2

import androidx.lifecycle.lifecycleScope
import com.sample.domain.usecases.requestLogin
import com.sample.domain.usecases.requestRegister
import com.sample.entities.AuthenticationResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.launch

/**
 * making the model() as an extension function on the Fragment to
 * limit the function to be used in this fragment, it is a matter of name space
 */
fun LoginFragment.model(channels: Pair<Channel<Intents>, SendChannel<UiModel>>) =
    lifecycleScope.handleIntentsAndUpdateUiModels(channels.first, channels.second)


private fun CoroutineScope.handleIntentsAndUpdateUiModels(
    intents: Channel<Intents>,
    uiModels: SendChannel<UiModel>,
    useCases: LoginUseCases = LoginUseCases(intents, uiModels)
) = launch(SupervisorJob() + Dispatchers.IO) {
    uiModels.send(UiModel())
    for (intent in intents) {
        when (intent) {
            is RequestLogin -> useCases.login(intent)
            is RequestRegister -> useCases.register(intent)
        }
    }
}

/**
 * grouping dependencies in this data class for replacing them in unit-testing
 */
private data class LoginUseCases(
    val intents: Channel<Intents>,
    val uiModels: SendChannel<UiModel>,
    val loginUseCase: suspend (String?, String?) -> AuthenticationResponse = loginRequest,
    val registerUseCase: suspend (String?, String?) -> AuthenticationResponse = registerRequest,
    val login: suspend (RequestLogin) -> Unit = loginRequester(uiModels, loginUseCase),
    val register: suspend (RequestRegister) -> Unit = registerRequester(uiModels, registerUseCase)
)

private fun registerRequester(
    view: SendChannel<UiModel>,
    registerUseCase: suspend (String?, String?) -> AuthenticationResponse
): suspend (RequestRegister) -> Unit = {
    view.send(UiModel(progress = true))
    val response = registerUseCase(it.userName, it.password)
    view.send(UiModel(response.errorMessage, false, response))
}


private fun loginRequester(
    view: SendChannel<UiModel>,
    loginUseCase: suspend (String?, String?) -> AuthenticationResponse
): suspend (RequestLogin) -> Unit = {
    view.send(UiModel(progress = true))
    val response = loginUseCase(it.userName, it.password)
    view.send(UiModel(response.errorMessage, false, response))
}


private val loginRequest: suspend (String?, String?) -> AuthenticationResponse =
    { userName, password -> requestLogin(userName, password).blockingGet() }

private val registerRequest: suspend (String?, String?) -> AuthenticationResponse =
    { userName, password -> requestRegister(userName, password).blockingGet() }


