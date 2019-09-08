package com.sample.domain.usecases

import com.sample.domain.repositories.authentication.AuthenticationRepository
import com.sample.domain.repositories.authentication.authenticationRepository
import com.sample.entities.AuthenticationResponse
import com.sample.entities.UserCredentials
import io.reactivex.Single

private const val MINIMUM_USER_CREDENTIALS_LETTERS = 5

private fun String?.isValidUserCredentials(): Boolean {
    return this != null && !isBlank() && this.length >= MINIMUM_USER_CREDENTIALS_LETTERS
}

fun requestLogin(
    userName: String?,
    password: String?,
    repository: AuthenticationRepository = authenticationRepository
): Single<AuthenticationResponse> {

    return repository.takeIf { userName.isValidUserCredentials() && password.isValidUserCredentials() }
        ?.requestLogin(UserCredentials(userName!!, password!!))
        ?.flatMap { repository.saveToken(it) }
        ?.onErrorReturn { AuthenticationResponse(false, null, it.message ?: it.toString()) }
        ?: Single.just(AuthenticationResponse(false, null, "invalid username or password"))
}


fun requestRegister(
    userName: String?,
    password: String?,
    repository: AuthenticationRepository = authenticationRepository
): Single<AuthenticationResponse> {

    return repository.takeIf { userName.isValidUserCredentials() && password.isValidUserCredentials() }
        ?.requestRegister(UserCredentials(userName!!, password!!))
        ?.flatMap { repository.saveToken(it) }
        ?: Single.just(AuthenticationResponse(false, null, "invalid username or password"))
}

fun isLoggedInUser(repository: AuthenticationRepository = authenticationRepository): Single<Boolean> {
    return repository.hasSavedToken()
}