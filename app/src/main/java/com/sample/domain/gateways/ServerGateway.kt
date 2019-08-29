package com.sample.domain.gateways

import com.sample.entities.AuthenticationResponse
import io.reactivex.Single

val serverGateway by lazy { ServerGatewayImplementer() }


interface ServerGateway {

    fun requestLogin(userName: String, password: String): Single<AuthenticationResponse>

    fun requestRegister(userName: String, password: String): Single<AuthenticationResponse>

}


class ServerGatewayImplementer : ServerGateway {

    override fun requestLogin(userName: String, password: String): Single<AuthenticationResponse> {
        return Single.fromCallable {
            Thread.sleep(2000)
            AuthenticationResponse(true, "mocked-token", null)
        }
    }

    override fun requestRegister(
        userName: String,
        password: String
    ): Single<AuthenticationResponse> {
        return Single.fromCallable {
            Thread.sleep(2000)
            AuthenticationResponse(true, "mocked-token", null)
        }
    }

}