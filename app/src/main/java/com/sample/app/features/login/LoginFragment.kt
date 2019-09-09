package com.sample.app.features.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.sample.app.R
import com.sample.app.core.InflatableFragment
import com.sample.app.core.InflatableFragmentDelegate
import com.sample.app.features.home.HomeActivity
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

/**
 * using a [InflatableFragmentDelegate] to remove the boilerplate code for setting up
 * the Fragment's UI
 */
@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
class LoginFragment : Fragment(),
    InflatableFragment by InflatableFragmentDelegate(R.layout.fragment_login) {

    /** in MVI this is considered the View function */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(ViewModelProviders.of(this).get(LoginViewModels::class.java)) {

            lifecycleScope.launch(Dispatchers.Main) {

                /** here we observe on any view Models to update the UI */
                for (model in models) {
                    updateProgress(model)
                    updateLoginButton(model, intents)
                    updateRegisterButton(model, intents)
                    updateErrorTextView(model)
                    updateNavigation(model)
                }

            }
        }

    }

    private fun updateNavigation(model: Model) {
        model.authenticationResponse
            ?.takeIf { it.success }
            ?.also {
                startActivity(Intent(context, HomeActivity::class.java))
                activity?.finish()
            }
    }

    private fun updateErrorTextView(model: Model) {
        errorTextView.text = model.error ?: ""
    }


    private fun updateProgress(model: Model) {
        progressBar.visibility = if (model.progress) View.VISIBLE else View.GONE
    }

    /**
     * here we need the Intents Channel to be able to send user events to it,
     * which will cause the View Models to be updated later
     */
    private fun updateLoginButton(model: Model, intents: Channel<Intents>) {
        if (model.progress) loginButton.setOnClickListener(null)
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

    /**
     * here we need the Intents Channel to be able to send user events to it,
     * which will cause the View Models to be updated later
     */
    private fun updateRegisterButton(model: Model, intents: Channel<Intents>) {
        if (model.progress) registerButton.setOnClickListener(null)
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