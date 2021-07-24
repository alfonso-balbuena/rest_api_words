package com.alfonso.service

import com.alfonso.model.ServiceResponse
import com.alfonso.model.database.TagDB
import com.alfonso.model.request.TagRequest

interface TagService {
    fun addTag(userId : String, tag : TagRequest) : ServiceResponse<Any>
    fun updateTag(userId: String,tag : TagRequest) : ServiceResponse<Any>
    fun getTag(userId: String) : ServiceResponse<List<TagDB>>
}