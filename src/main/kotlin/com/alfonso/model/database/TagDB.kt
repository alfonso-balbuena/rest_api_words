package com.alfonso.model.database

import kotlinx.serialization.Serializable

@Serializable
data class TagDB(val name: String, val color : Int?)
