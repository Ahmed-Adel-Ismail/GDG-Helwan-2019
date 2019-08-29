package com.sample.domain.repositories.authentication

import com.sample.entities.AuthenticationResponse
import com.sample.entities.UserCredentials
import io.reactivex.Maybe
import io.reactivex.Single

val authenticationRepository by lazy { AuthenticationRepositoryImplementer() }

interface AuthenticationRepository {

    fun requestLogin(credentials: UserCredentials): Single<AuthenticationResponse>

    fun requestRegister(credentials: UserCredentials): Single<AuthenticationResponse>

    fun saveToken(authenticationResponse: AuthenticationResponse): Single<AuthenticationResponse>

    fun hasSavedToken(): Single<Boolean>

    fun loadToken(): Maybe<String>

}