package com.alfonso.model.database

import kotlinx.serialization.Serializable

@Serializable
data class TagCollectionDB(var _id: String?,val userId: String,val tags : ArrayList<TagDB>)
