package com.alfonso.tag.model.database

import kotlinx.serialization.Serializable

@Serializable
data class TagDB(val name: String, val color : Int?)
