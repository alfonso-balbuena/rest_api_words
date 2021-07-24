package com.alfonso.model.database

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class TokenDB(val token : String,@Contextual val date: Date)
