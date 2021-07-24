package com.alfonso.login.util

import com.alfonso.general.model.response.GenericResponse
import com.alfonso.general.model.response.NothingResponse
import com.alfonso.general.response.GeneralResponse
import com.alfonso.general.response.Response

class LoginResponseRest {
    companion object {
        sealed class LoginResponseCode(val code: Int,val message : String) {
            object NoUserError : LoginResponseCode(10,"User no found")
            object UserExistError : LoginResponseCode(11,"The user exists already")
        }

        fun getNoUserErrorResponse() : GenericResponse<NothingResponse> {
            return getResponseLogin(LoginResponseCode.NoUserError)
        }

        fun getUserExistErrorResponse() : GenericResponse<NothingResponse> {
            return getResponseLogin(LoginResponseCode.UserExistError)
        }

        private fun getResponseLogin(response: LoginResponseCode) : GenericResponse<NothingResponse> {
            return GenericResponse(response.code,response.message, NothingResponse())
        }
    }





}