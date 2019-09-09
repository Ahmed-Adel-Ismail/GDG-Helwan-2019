package com.sample.app.features.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.sample.app.R
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private val model by lazy { ViewModelProviders.of(this).get(Model::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch(Dispatchers.Main) {
            view(model)
        }
    }

    private suspend fun view(model: Model) {

        for (viewState in model.viewStates) {

            if (viewState.progress) showLoadingUi() else showResumedUi(model.intents)

            errorTextView.text = viewState.error ?: ""

            viewState.authenticationResponse
                ?.takeIf { it.success }
                ?.also { /* navigate to next screen */ }
        }

    }

    private fun showLoadingUi() {
        progressBar.visibility = View.VISIBLE
        loginButton.setOnClickListener(null)
        registerButton.setOnClickListener(null)
    }

    private fun showResumedUi(intents: Channel<Intents>) {

        progressBar.visibility = View.GONE

        loginButton.setOnClickListener {
            lifecycleScope.launch {
                notifyRequestLogin(intents)
            }
        }

        registerButton.setOnClickListener {
            lifecycleScope.launch {
                notifyRequestRegister(intents)
            }
        }
    }

    private suspend fun notifyRequestRegister(intents: Channel<Intents>) {
        intents.send(
            RequestRegister(
                "${userNameEditText.text}",
                "${passwordEditText.text}"
            )
        )
    }

    private suspend fun notifyRequestLogin(intents: Channel<Intents>) {
        intents.send(
            RequestLogin(
                "${userNameEditText.text}",
                "${passwordEditText.text}"
            )
        )
    }


}