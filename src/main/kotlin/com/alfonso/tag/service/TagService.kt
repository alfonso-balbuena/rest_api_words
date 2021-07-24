package com.alfonso.tag.service

import com.alfonso.general.model.ServiceResponse
import com.alfonso.tag.model.database.TagDB
import com.alfonso.tag.model.request.TagRequest

interface TagService {
    suspend fun addTag(userId : String, tag : TagRequest) : ServiceResponse<Any>
    suspend fun updateTag(userId: String, tag : TagRequest) : ServiceResponse<Any>
    suspend fun getTag(userId: String) : ServiceResponse<List<TagDB>>
}