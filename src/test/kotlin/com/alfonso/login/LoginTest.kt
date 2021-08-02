package com.alfonso.login

import com.alfonso.general.model.response.GenericResponse
import com.alfonso.general.model.response.NothingResponse
import com.alfonso.login.model.database.getTestObject
import com.alfonso.login.model.response.UserResponse
import com.alfonso.login.repository.fake.RepositoryUserFake
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class LoginTest : RoutingLoginTest() {

    @Test
    fun testLoginSuccess() {
        applicationTest {
            with(it.handleRequest(HttpMethod.Post, "/login"){
                addHeader(HttpHeaders.ContentType,"application/json")
                setBody("{\"id\" : \"113031883981083652781\", \"provider\" : \"google\"}")
            }) {
                print(response.content)
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
    fun testLoginUserNoFound() {
        applicationTest {
            val fakeRepository = repositoryUserFake as RepositoryUserFake
            fakeRepository.isReturningNull = true
            with(it.handleRequest(HttpMethod.Post, "/login"){
                addHeader(HttpHeaders.ContentType,"application/json")
                setBody("{\"id\" : \"113031883981083652781\", \"provider\" : \"google\"}")
            }) {
                print(response.content)
                assertEquals(200, response.status()?.value)
                assertNotNull(response.content)
                val responseObject = Json.decodeFromString<GenericResponse<NothingResponse>>(response.content!!)
                assertEquals(10,responseObject.code)
                assertEquals("User no found",responseObject.message)
            }
        }
    }

    @Test
    fun testLoginNoData() {
        applicationTest {
            val fakeRepository = repositoryUserFake as RepositoryUserFake
            fakeRepository.isReturningNull = true
            with(it.handleRequest(HttpMethod.Post, "/login"){
                addHeader(HttpHeaders.ContentType,"application/json")
                setBody("{}")
            }) {
                print(response.content)
                assertEquals(200, response.status()?.value)
                val responseObject = Json.decodeFromString<GenericResponse<NothingResponse>>(response.content!!)
                assertEquals(2,responseObject.code)
                assertEquals("The fields id, provider are required",responseObject.message)
            }
        }
    }

    @Test
    fun testLoginOnlyId() {
        applicationTest {
            val fakeRepository = repositoryUserFake as RepositoryUserFake
            fakeRepository.isReturningNull = true
            with(it.handleRequest(HttpMethod.Post, "/login"){
                addHeader(HttpHeaders.ContentType,"application/json")
                setBody("{\"id\" : \"113031883981083652781\"}")
            }) {
                print(response.content)
                assertEquals(200, response.status()?.value)
                val responseObject = Json.decodeFromString<GenericResponse<NothingResponse>>(response.content!!)
                assertEquals(2,responseObject.code)
                assertEquals("The fields provider are required",responseObject.message)
            }
        }
    }

    @Test
    fun testLoginOnlyProvider() {
        applicationTest {
            val fakeRepository = repositoryUserFake as RepositoryUserFake
            fakeRepository.isReturningNull = true
            with(it.handleRequest(HttpMethod.Post, "/login"){
                addHeader(HttpHeaders.ContentType,"application/json")
                setBody("{\"provider\" : \"google\"}")
            }) {
                print(response.content)
                assertEquals(200, response.status()?.value)
                val responseObject = Json.decodeFromString<GenericResponse<NothingResponse>>(response.content!!)
                assertEquals(2,responseObject.code)
                assertEquals("The fields id are required",responseObject.message)
            }
        }
    }

    @Test
    fun testLoginNothing() {
        applicationTest {
            val fakeRepository = repositoryUserFake as RepositoryUserFake
            fakeRepository.isReturningNull = true
            with(it.handleRequest(HttpMethod.Post, "/login") {
                addHeader(HttpHeaders.ContentType, "application/json")
                setBody("")
            }) {
                print(response.content)
                assertEquals(200, response.status()?.value)
                val responseObject = Json.decodeFromString<GenericResponse<NothingResponse>>(response.content!!)
                assertEquals(3, responseObject.code)
                assertEquals("There are problems with the request body", responseObject.message)
            }
        }
    }
}

