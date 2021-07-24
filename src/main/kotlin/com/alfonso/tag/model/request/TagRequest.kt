package com.alfonso.tag.model.request

import kotlinx.serialization.Serializable

@Serializable
data class TagRequest(val name: String,val color : Int? = null)
