package com.alfonso.general.model.request

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(val id: String = "", val token: String = "") {

    fun validateAuthRequest() : List<String> {
        val fields = ArrayList<String>()
        if(id.isBlank() || id.isEmpty())
            fields.add("id")
        if(token.isBlank() || token.isEmpty())
            fields.add("token")
        return fields
    }
}
