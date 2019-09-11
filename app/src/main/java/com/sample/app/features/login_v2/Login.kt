@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.sample.app.features.login_v2

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sample.app.R
import com.sample.app.core.InflatableFragment
import com.sample.app.core.InflatableFragmentDelegate

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportFragmentManager?.takeIf { savedInstanceState == null }
            ?.beginTransaction()
            ?.replace(R.id.container, LoginFragment())
            ?.commit()
    }
}

/**
 * using a [InflatableFragmentDelegate] to remove the boilerplate code for setting up
 * the Fragment's UI
 */
@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
class LoginFragment : Fragment(),
    InflatableFragment by InflatableFragmentDelegate(R.layout.fragment_login) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model(view(intents()))
    }


}



