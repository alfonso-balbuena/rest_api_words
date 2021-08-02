package com.alfonso.login

import com.alfonso.general.model.response.GenericResponse
import com.alfonso.general.model.response.NothingResponse
import com.alfonso.login.repository.fake.RepositoryUserFake
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.Test
import kotlin.test.assertEquals

class LogoutTest : RoutingLoginTest() {

    @Test
    fun testLogoutSuccess() {
        applicationTest {
            with(it.handleRequest(HttpMethod.Post, "/logout"){
                addHeader(HttpHeaders.ContentType,"application/json")
                setBody("{\"auth\" : {\"id\" : \"60fb3768500411023ccfb\", \"token\" : \"MjQxIDAyOjQxOjIx\"}}")
            }) {
                assertEquals(200,response.status()?.value)
                val responseObject = Json.decodeFromString<GenericResponse<NothingResponse>>(response.content!!)
                assertEquals(0,responseObject.code)
            }
        }
    }

    @Test
    fun testLogoutNoUserError() {
        applicationTest {
            val fakeRepository = repositoryUserFake as RepositoryUserFake
            fakeRepository.isGetUserIdNull = true
            with(it.handleRequest(HttpMethod.Post, "/logout"){
                addHeader(HttpHeaders.ContentType,"application/json")
                setBody("{\"auth\" : {\"id\" : \"60fb3768500411023ccfb\", \"token\" : \"MjQxIDAyOjQxOjIx\"}}")
            }) {
                assertEquals(200,response.status()?.value)
                val responseObject = Json.decodeFromString<GenericResponse<NothingResponse>>(response.content!!)
                assertEquals(10,responseObject.code)
            }
        }
    }

    @Test
    fun testLogoutUnexpectedError() {
        applicationTest {
            val fakeRepository = repositoryUserFake as RepositoryUserFake
            fakeRepository.isUpdateUserNoUpdate = true
            with(it.handleRequest(HttpMethod.Post, "/logout"){
                addHeader(HttpHeaders.ContentType,"application/json")
                setBody("{\"auth\" : {\"id\" : \"60fb3768500411023ccfb\", \"token\" : \"MjQxIDAyOjQxOjIx\"}}")
            }) {
                assertEquals(200,response.status()?.value)
                val responseObject = Json.decodeFromString<GenericResponse<NothingResponse>>(response.content!!)
                assertEquals(1,responseObject.code)
            }
        }
    }


    @Test
    fun testLogoutNoBodyError() {
        applicationTest {
            val fakeRepository = repositoryUserFake as RepositoryUserFake
            fakeRepository.isUpdateUserNoUpdate = true
            with(it.handleRequest(HttpMethod.Post, "/logout"){
                addHeader(HttpHeaders.ContentType,"application/json")
                setBody("")
            }) {
                assertEquals(200,response.status()?.value)
                val responseObject = Json.decodeFromString<GenericResponse<NothingResponse>>(response.content!!)
                assertEquals(3,responseObject.code)
            }
        }
    }

    @Test
    fun testLogoutMissingAllFields() {
        applicationTest {
            with(it.handleRequest(HttpMethod.Post, "/logout"){
                addHeader(HttpHeaders.ContentType,"application/json")
                setBody("{}")
            }) {
                assertEquals(200,response.status()?.value)
                val responseObject = Json.decodeFromString<GenericResponse<NothingResponse>>(response.content!!)
                assertEquals(2,responseObject.code)
            }
        }
    }
}