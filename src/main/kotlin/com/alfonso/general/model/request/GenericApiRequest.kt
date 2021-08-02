package com.alfonso.general.model.request

import kotlinx.serialization.Serializable

@Serializable
data class GenericApiRequest<out T>(val auth: AuthRequest = AuthRequest(), val data : T? = null) {

    fun validateAuth() : ArrayList<String> {
        val fields = ArrayList<String>()
        if(auth.id.isBlank() || auth.id.isEmpty()) {
            fields.add("Auth.id")
        }
        if(auth.token.isEmpty() || auth.token.isBlank()) {
            fields.add("Auth.token")
        }
        return fields
    }
}
