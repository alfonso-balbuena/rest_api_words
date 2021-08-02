package com.alfonso.login.model.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(val id: String = "", val provider: String = "") {
    fun validateLoginRequest() : List<String> {
        val fieldsMissing = ArrayList<String>()
        if(id.isEmpty() || id.isBlank())
            fieldsMissing.add("id")
        if(provider.isEmpty() || provider.isBlank())
            fieldsMissing.add("provider")
        return fieldsMissing
    }
}
