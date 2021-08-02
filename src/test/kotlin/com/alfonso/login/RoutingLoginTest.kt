package com.alfonso.login

import com.alfonso.apikey.service.AuthService
import com.alfonso.apikey.service.imp.AuthServiceFake
import com.alfonso.login.repository.IRepositoryUser
import com.alfonso.login.repository.fake.RepositoryUserFake
import com.alfonso.login.service.LoginService
import com.alfonso.login.service.TokenService
import com.alfonso.login.service.imp.LoginServiceImp
import com.alfonso.login.service.imp.TokenServiceFake
import com.alfonso.module
import io.ktor.application.*
import io.ktor.server.testing.*
import org.koin.dsl.module
import org.koin.ktor.ext.Koin
import org.koin.test.KoinTest
import org.koin.test.inject

open class RoutingLoginTest : KoinTest {
    protected val repositoryUserFake by inject<IRepositoryUser>()

    private val loginModuleTest = module {
        single<LoginService> { LoginServiceImp(get(),get()) }
        single<TokenService> { TokenServiceFake() }
        single<IRepositoryUser> { RepositoryUserFake() }
    }

    private val authModuleTest = module {
        single<AuthService> { AuthServiceFake() }
    }

    private fun Application.koinTest() {
        install(Koin) {
            modules(loginModuleTest)
            modules(authModuleTest)
        }
    }

    protected fun <R> applicationTest(testRouting : (TestApplicationEngine) -> R) {
        withTestApplication({
            koinTest()
            module(true)
        }) {
            testRouting(this)
        }
    }
}