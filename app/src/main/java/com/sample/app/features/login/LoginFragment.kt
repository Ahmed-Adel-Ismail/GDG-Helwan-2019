package com.sample.app.features.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.sample.app.R
import com.sample.app.features.home.HomeActivity
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
            updateProgress(viewState)
            updateLoginButton(viewState, model.intents)
            updateRegisterButton(viewState, model.intents)
            updateErrorTextView(viewState)
            updateNavigation(viewState)
        }
    }

    private fun updateNavigation(viewState: ViewState) {
        viewState.authenticationResponse
            ?.takeIf { it.success }
            ?.also {
                startActivity(Intent(context, HomeActivity::class.java))
                activity?.finish()
            }
    }

    private fun updateErrorTextView(viewState: ViewState) {
        errorTextView.text = viewState.error ?: ""
    }


    private fun updateProgress(viewState: ViewState) {
        progressBar.visibility = if (viewState.progress) View.VISIBLE else View.GONE
    }

    private fun updateLoginButton(viewState: ViewState, intents: Channel<Intents>) {
        if (viewState.progress) loginButton.setOnClickListener(null)
        else loginButton.setOnClickListener {
            lifecycleScope.launch {
                intents.send(
                    RequestLogin(
                        "${userNameEditText.text}",
                        "${passwordEditText.text}"
                    )
                )
            }
        }
    }

    private fun updateRegisterButton(viewState: ViewState, intents: Channel<Intents>) {
        if (viewState.progress) registerButton.setOnClickListener(null)
        else registerButton.setOnClickListener {
            lifecycleScope.launch {
                intents.send(
                    RequestRegister(
                        "${userNameEditText.text}",
                        "${passwordEditText.text}"
                    )
                )
            }
        }
    }


}