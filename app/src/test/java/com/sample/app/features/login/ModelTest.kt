package com.sample.app.features.login

import com.sample.entities.AuthenticationResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.isActive
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class ModelTest {

    @Test
    fun onInitializeThenReturnProgressDisabled() {

        runBlocking {

            with(Model()) {

                val result = viewStates.receive()

                assertFalse(result.progress)

            }
        }

    }

    @Test
    fun onRequestRegisterStartedThenShowProgress() {
        runBlocking {

            with(Model(registerUseCase = { _, _ -> AuthenticationResponse(true) })) {

                intents.send(RequestRegister("", ""))

                val results = listOf(
                    viewStates.receive(),
                    viewStates.receive()
                )

                assertTrue(results[1].progress)
            }
        }
    }

    @Test
    fun onRequestRegisterFinishedThenHideProgress() {
        runBlocking {

            with(Model(registerUseCase = { _, _ -> AuthenticationResponse(true) })) {

                intents.send(RequestRegister("", ""))

                val results = listOf(
                    viewStates.receive(),
                    viewStates.receive(),
                    viewStates.receive()
                )

                assertFalse(results[2].progress)
            }
        }
    }

    @Test
    fun onRequestRegisterFailedThenShowErrorMessage() {

        runBlocking {

            with(Model(registerUseCase = { _, _ -> AuthenticationResponse(errorMessage = "ERROR") })) {

                intents.send(RequestRegister("", ""))

                val results = listOf(
                    viewStates.receive(),
                    viewStates.receive(),
                    viewStates.receive()
                )

                assertNotNull(results[2].error)
            }
        }
    }

    @Test
    fun onRequestLoginStartedThenShowProgress() {
        runBlocking {

            with(Model(loginUseCase = { _, _ -> AuthenticationResponse(true) })) {

                intents.send(RequestLogin("", ""))

                val results = listOf(
                    viewStates.receive(),
                    viewStates.receive()
                )

                assertTrue(results[1].progress)
            }
        }
    }

    @Test
    fun onRequestLoginFinishedThenHideProgress() {
        runBlocking {

            with(Model(loginUseCase = { _, _ -> AuthenticationResponse(true) })) {

                intents.send(RequestLogin("", ""))

                val results = listOf(
                    viewStates.receive(),
                    viewStates.receive(),
                    viewStates.receive()
                )

                assertFalse(results[2].progress)
            }
        }
    }

    @Test
    fun onRequestLoginFailedThenShowErrorMessage() {

        runBlocking {

            with(Model(loginUseCase = { _, _ -> AuthenticationResponse(errorMessage = "ERROR") })) {

                intents.send(RequestLogin("", ""))

                val results = listOf(
                    viewStates.receive(),
                    viewStates.receive(),
                    viewStates.receive()
                )

                assertNotNull(results[2].error)
            }
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun onClearedThenCancelIntentsChannel() {

        runBlocking {

            with(Model()) {

                onCleared()

                assertTrue(intents.isClosedForReceive && intents.isClosedForSend)

            }
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun onClearedThenCancelViewStatesChannel() {

        runBlocking {

            with(Model()) {

                onCleared()

                assertTrue(viewStates.isClosedForReceive && viewStates.isClosedForSend)

            }
        }
    }

    @Test
    fun onClearedThenCancelCoroutineScope() {

        runBlocking {

            with(Model()) {

                onCleared()

                assertFalse(coroutineScope.isActive)

            }
        }
    }

}