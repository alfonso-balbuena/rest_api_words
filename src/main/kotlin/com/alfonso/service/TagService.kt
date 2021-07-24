package com.alfonso.service

import com.alfonso.model.ServiceResponse
import com.alfonso.model.database.TagDB
import com.alfonso.model.request.TagRequest

interface TagService {
    suspend fun addTag(userId : String, tag : TagRequest) : ServiceResponse<Any>
    suspend fun updateTag(userId: String, tag : TagRequest) : ServiceResponse<Any>
    suspend fun getTag(userId: String) : ServiceResponse<List<TagDB>>
}