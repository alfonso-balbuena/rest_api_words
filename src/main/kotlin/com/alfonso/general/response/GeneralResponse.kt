package com.alfonso.general.response

import com.alfonso.general.model.response.GenericResponse
import com.alfonso.general.model.response.NothingResponse

class GeneralResponse {

    companion object {
        fun getUnexpectedErrorResponse() : GenericResponse<NothingResponse> {
            return getGenericResponse(Response.UnexpectedErrorResponse)
        }

        fun getSuccess() : GenericResponse<NothingResponse> {
            return getGenericResponse(Response.Success)
        }

        fun <T> getSuccessWithData(data: T) : GenericResponse<T> {
            val successWithData = Response.SuccessWithData(data)
            return GenericResponse(successWithData.code,successWithData.message,data)
        }

        private fun getGenericResponse(response: Response) : GenericResponse<NothingResponse> {
            return GenericResponse(response.code, response.message, NothingResponse())
        }
    }
}