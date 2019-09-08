package com.sample.app.features.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sample.app.R

class LoginActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager?.takeIf { savedInstanceState == null }
            ?.beginTransaction()
            ?.replace(R.id.container,LoginFragment())
            ?.commit()
    }
}


