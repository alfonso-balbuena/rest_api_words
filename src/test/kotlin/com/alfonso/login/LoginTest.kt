package com.alfonso.login

import com.alfonso.apikey.service.AuthService
import com.alfonso.apikey.service.imp.AuthServiceFake
import com.alfonso.general.model.response.GenericResponse
import com.alfonso.general.model.response.NothingResponse
import com.alfonso.login.model.database.getTestObject
import com.alfonso.login.model.response.UserResponse
import com.alfonso.login.repository.IRepositoryUser
import com.alfonso.login.repository.fake.RepositoryUserFake
import com.alfonso.login.service.LoginService
import com.alfonso.login.service.TokenService
import com.alfonso.login.service.imp.LoginServiceImp
import com.alfonso.login.service.imp.TokenServiceFake
import com.alfonso.login.service.imp.TokenServiceImp
import com.alfonso.module
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.Test
import org.koin.dsl.module
import org.koin.ktor.ext.Koin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class LoginTest : KoinTest {

    val authServiceFake by inject<AuthService>()
    val repositoryUserFake by inject<IRepositoryUser>()

    private val loginModuleTest = module {
        single<LoginService> { LoginServiceImp(get(),get()) }
        single<TokenService> { TokenServiceFake() }
        single<IRepositoryUser> { RepositoryUserFake() }
    }

    private val authModuleTest = module {
        single<AuthService> { AuthServiceFake() }
    }

    @Test
    fun testLoginSuccess() {
        withTestApplication({
            koinTest()
            module(true)
        }) {
            with(handleRequest(HttpMethod.Post, "/login"){
                addHeader(HttpHeaders.ContentType,"application/json")
                setBody("{\"id\" : \"113031883981083652781\", \"provider\" : \"google\"}")
            }) {
                assertEquals(200, response.status()?.value)
                assertNotNull(response.content)
                val fakeResponse = UserResponse.getTestObject()
                val responseObject = Json.decodeFromString<GenericResponse<UserResponse>>(response.content!!)
                assertEquals(fakeResponse,responseObject.data)
                assertEquals(0,responseObject.code)

            }
        }
    }

    @Test
    fun testLoginFail() {
        withTestApplication({
            koinTest()
            module(true)
        }) {
            val fakeRepository = repositoryUserFake as RepositoryUserFake
            fakeRepository.hasError = true
            with(handleRequest(HttpMethod.Post, "/login"){
                addHeader(HttpHeaders.ContentType,"application/json")
                setBody("{\"id\" : \"113031883981083652781\", \"provider\" : \"google\"}")
            }) {
                assertEquals(200, response.status()?.value)
                assertNotNull(response.content)
                val responseObject = Json.decodeFromString<GenericResponse<NothingResponse>>(response.content!!)
                assertEquals(10,responseObject.code)
            }
        }
    }

    private fun Application.koinTest() {
        install(Koin) {
            modules(loginModuleTest)
            modules(authModuleTest)
        }
    }
}

