package com.alfonso.login

import com.alfonso.general.model.request.AuthRequest
import com.alfonso.login.model.database.UserDB
import com.alfonso.login.model.database.getTestUserDB
import com.alfonso.login.model.request.LoginRequest
import com.alfonso.login.model.request.UserAddRequest
import com.alfonso.login.repository.fake.RepositoryUserFake
import com.alfonso.login.service.LoginServiceResponse
import com.alfonso.login.service.imp.LoginServiceImp
import com.alfonso.login.service.imp.TokenServiceFake
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

@ExperimentalCoroutinesApi
class LoginServiceTest {

    lateinit var loginService : LoginServiceImp
    lateinit var repositoryFake : RepositoryUserFake
    @Before
    fun initLoginService() {
        repositoryFake = RepositoryUserFake()
        loginService = LoginServiceImp(repositoryFake,TokenServiceFake())
    }

    @Test
    fun loginNoUserError() = runBlocking {
        repositoryFake.isReturningNull = true
        val response = loginService.login(LoginRequest("1","google"))
        assertEquals(LoginServiceResponse.NoUserError,response)
    }

    @Test
    fun loginSuccess() = runBlocking {
        repositoryFake.isReturningNull = false
        val userFake = UserDB.getTestUserDB()
        val response = loginService.login(LoginRequest("1","google"))
        assertIs<LoginServiceResponse.Success>(response)
        assertEquals(userFake._id,response.value._id)
        assertEquals("token",response.value.token)
        assertEquals(userFake.email,response.value.email)
    }

    @Test
    fun registerUserExistError(): Unit = runBlocking {
        repositoryFake.isReturningNull = false
        val response = loginService.register(UserAddRequest("","","","",""))
        assertIs<LoginServiceResponse.UserExistError>(response)
    }

    @Test
    fun registerUserUnExpectedError(): Unit = runBlocking {
        repositoryFake.isReturningNull = true
        val response = loginService.register(UserAddRequest("","","","",""))
        assertIs<LoginServiceResponse.UnexpectedError>(response)
    }

    @Test
    fun registerUserSuccess() {
        //TODO
    }

    @Test
    fun logoutNoUserError(): Unit = runBlocking {
        repositoryFake.isReturningNull = true
        val response = loginService.logout(AuthRequest("1","token"))
        assertIs<LoginServiceResponse.NoUserError>(response)
    }

    @Test
    fun logoutSuccess(): Unit = runBlocking {
        repositoryFake.isReturningNull = false
        val response = loginService.logout(AuthRequest("1","token"))
        assertIs<LoginServiceResponse.Success>(response)
    }
}