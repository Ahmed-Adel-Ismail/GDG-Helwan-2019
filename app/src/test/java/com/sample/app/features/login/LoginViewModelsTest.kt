package com.sample.app.features.login

import com.sample.entities.AuthenticationResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.isActive
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class LoginViewModelsTest {

    @Test
    fun onInitializeThenReturnProgressDisabled() {

        runBlocking {

            with(LoginViewModels()) {

                val result = models.receive()

                assertFalse(result.progress)

            }
        }

    }

    @Test
    fun onRequestRegisterStartedThenShowProgress() {
        runBlocking {

            with(LoginViewModels(registerUseCase = { _, _ -> AuthenticationResponse(true) })) {

                intents.send(RequestRegister("", ""))

                val results = listOf(
                    models.receive(),
                    models.receive()
                )

                assertTrue(results[1].progress)
            }
        }
    }

    @Test
    fun onRequestRegisterFinishedThenHideProgress() {
        runBlocking {

            with(LoginViewModels(registerUseCase = { _, _ -> AuthenticationResponse(true) })) {

                intents.send(RequestRegister("", ""))

                val results = listOf(
                    models.receive(),
                    models.receive(),
                    models.receive()
                )

                assertFalse(results[2].progress)
            }
        }
    }

    @Test
    fun onRequestRegisterFailedThenShowErrorMessage() {

        runBlocking {

            with(LoginViewModels(registerUseCase = { _, _ -> AuthenticationResponse(errorMessage = "ERROR") })) {

                intents.send(RequestRegister("", ""))

                val results = listOf(
                    models.receive(),
                    models.receive(),
                    models.receive()
                )

                assertNotNull(results[2].error)
            }
        }
    }

    @Test
    fun onRequestLoginStartedThenShowProgress() {
        runBlocking {

            with(LoginViewModels(loginUseCase = { _, _ -> AuthenticationResponse(true) })) {

                intents.send(RequestLogin("", ""))

                val results = listOf(
                    models.receive(),
                    models.receive()
                )

                assertTrue(results[1].progress)
            }
        }
    }

    @Test
    fun onRequestLoginFinishedThenHideProgress() {
        runBlocking {

            with(LoginViewModels(loginUseCase = { _, _ -> AuthenticationResponse(true) })) {

                intents.send(RequestLogin("", ""))

                val results = listOf(
                    models.receive(),
                    models.receive(),
                    models.receive()
                )

                assertFalse(results[2].progress)
            }
        }
    }

    @Test
    fun onRequestLoginFailedThenShowErrorMessage() {

        runBlocking {

            with(LoginViewModels(loginUseCase = { _, _ -> AuthenticationResponse(errorMessage = "ERROR") })) {

                intents.send(RequestLogin("", ""))

                val results = listOf(
                    models.receive(),
                    models.receive(),
                    models.receive()
                )

                assertNotNull(results[2].error)
            }
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun onClearedThenCancelIntentsChannel() {

        runBlocking {

            with(LoginViewModels()) {

                onCleared()

                assertTrue(intents.isClosedForReceive && intents.isClosedForSend)

            }
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun onClearedThenCancelViewStatesChannel() {

        runBlocking {

            with(LoginViewModels()) {

                onCleared()

                assertTrue(models.isClosedForReceive && models.isClosedForSend)

            }
        }
    }

    @Test
    fun onClearedThenCancelCoroutineScope() {

        runBlocking {

            with(LoginViewModels()) {

                onCleared()

                assertFalse(coroutineScope.isActive)

            }
        }
    }

}