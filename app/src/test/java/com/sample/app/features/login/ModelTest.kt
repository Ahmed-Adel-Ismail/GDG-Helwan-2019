package com.sample.app.features.login

import com.sample.entities.AuthenticationResponse
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class ModelTest {

    @Test
    fun onInitializeThenReturnProgressDisabled() {
        runBlocking {

            val model = Model(bufferCapacity = 1)
            val intents = Channel<Intents>(1)

            intents.send(Initialize)

            val result = model(intents).poll()

            assertFalse(result!!.progress)

        }
    }

    @Test
    fun onRequestRegisterStartedThenShowProgress() {

        runBlocking {

            val intents = Channel<Intents>(1)
            val model =
                Model(
                    bufferCapacity = 2,
                    registerUseCase = { _, _ -> AuthenticationResponse(true) })

            intents.send(RequestRegister("", ""))

            val viewState = model(intents).toList().first()

            assertTrue(viewState.progress)

        }

    }

    @Test
    fun onRequestRegisterFinishedThenHideProgress() {

        runBlocking {

            val intents = Channel<Intents>(1)
            val model =
                Model(
                    bufferCapacity = 2,
                    registerUseCase = { _, _ -> AuthenticationResponse(true) })

            intents.send(RequestRegister("", ""))

            val viewState = model(intents).toList().last()

            assertFalse(viewState.progress)

        }

    }

    @Test
    fun onRequestRegisterFailedThenShowErrorMessage() {

        runBlocking {

            val intents = Channel<Intents>(1)
            val model = Model(
                bufferCapacity = 2,
                registerUseCase = { _, _ -> AuthenticationResponse(errorMessage = "ERROR") }
            )

            intents.send(RequestRegister("", ""))

            val viewState = model(intents).toList()[1]

            assertNotNull(viewState.error)

        }

    }

    @Test
    fun onRequestLoginStartedThenShowProgress() {

        runBlocking {

            val intents = Channel<Intents>(1)
            val model =
                Model(bufferCapacity = 2, loginUseCase = { _, _ -> AuthenticationResponse(true) })

            intents.send(RequestLogin("", ""))

            val viewState = model(intents).toList().first()

            assertTrue(viewState.progress)

        }

    }

    @Test
    fun onRequestLoginFinishedThenHideProgress() {

        runBlocking {

            val intents = Channel<Intents>(1)
            val model =
                Model(bufferCapacity = 2, loginUseCase = { _, _ -> AuthenticationResponse(true) })

            intents.send(RequestLogin("", ""))

            val viewState = model(intents).toList().last()

            assertFalse(viewState.progress)

        }

    }

    @Test
    fun onRequestLoginFailedThenShowErrorMessage() {

        runBlocking {

            val intents = Channel<Intents>(1)
            val model = Model(
                bufferCapacity = 2,
                loginUseCase = { _, _ -> AuthenticationResponse(errorMessage = "ERROR") }
            )

            intents.send(RequestLogin("", ""))

            val viewState = model(intents).toList().last()

            assertNotNull(viewState.error)

        }

    }


}