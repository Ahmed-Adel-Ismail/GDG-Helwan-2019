package com.sample.app.features.login

import android.view.View.GONE
import android.view.View.VISIBLE
import com.sample.entities.AuthenticationResponse
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.launch

suspend fun LoginFragment.view(viewStates: ReceiveChannel<ViewState>) {

    val viewState = viewStates.receive()

    if (viewState.progress) {
        showLoadingUi()
    } else {
        showResumedUi()
    }

    errorTextView.text = viewState.error ?: ""

    viewState.authenticationResponse
        ?.takeIf { it.success }
        ?.also { /* navigate to next screen */ }

}

private fun LoginFragment.showResumedUi() {
    progressBar.visibility = GONE
    loginButton.setOnClickListener {
        launch {
            intents.send(RequestLogin("" + userNameEditText.text, "" + passwordEditText.text))
        }
    }
    registerButton.setOnClickListener {
        launch {
            intents.send(
                RequestRegister(
                    "" + userNameEditText.text,
                    "" + passwordEditText.text
                )
            )
        }
    }
}

private fun LoginFragment.showLoadingUi() {
    progressBar.visibility = VISIBLE
    loginButton.setOnClickListener(null)
    registerButton.setOnClickListener(null)
}


data class ViewState(
    val error: String? = null,
    val progress: Boolean = false,
    val authenticationResponse: AuthenticationResponse? = null
)