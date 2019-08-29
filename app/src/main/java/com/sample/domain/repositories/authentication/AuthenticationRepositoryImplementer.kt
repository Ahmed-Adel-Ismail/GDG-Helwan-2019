package com.sample.domain.repositories.authentication

import com.sample.domain.gateways.PreferencesGateway
import com.sample.domain.gateways.ServerGateway
import com.sample.domain.gateways.preferencesGateway
import com.sample.domain.gateways.serverGateway
import com.sample.entities.AuthenticationResponse
import com.sample.entities.UserCredentials
import io.reactivex.Maybe
import io.reactivex.Single

private const val KEY_TOKEN = "KEY_TOKEN"
private const val DEFAULT_VALUE_TOKEN = ""

class AuthenticationRepositoryImplementer(
    private val server: ServerGateway = serverGateway,
    private val preferences: PreferencesGateway = preferencesGateway
) : AuthenticationRepository {

    override fun requestLogin(credentials: UserCredentials): Single<AuthenticationResponse> {
        return server.requestLogin(credentials.userName, credentials.password)
    }

    override fun requestRegister(credentials: UserCredentials): Single<AuthenticationResponse> {
        return server.requestRegister(credentials.userName, credentials.password)
    }

    override fun saveToken(authenticationResponse: AuthenticationResponse): Single<AuthenticationResponse> {
        return Single.fromCallable {
            authenticationResponse.token?.also { preferences.save(KEY_TOKEN, it) }
            authenticationResponse
        }
    }

    override fun hasSavedToken(): Single<Boolean> {
        return preferences.isSaved(KEY_TOKEN)
    }

    override fun loadToken(): Maybe<String> {
        return preferences.load(
            KEY_TOKEN,
            DEFAULT_VALUE_TOKEN
        ).filter { it != DEFAULT_VALUE_TOKEN }
    }
}