package com.alfonso.service.imp

import com.alfonso.model.ServiceResponse
import com.alfonso.model.database.TagDB
import com.alfonso.model.request.TagRequest
import com.alfonso.repository.IRepositoryTag
import com.alfonso.service.TagService

class TagServiceImp(private val repositoryTag: IRepositoryTag) : TagService {

    override fun addTag(userId: String, tag: TagRequest): ServiceResponse<Any> {
        val tagCollection = repositoryTag.getTag(userId)
        tagCollection?.let {
            return insert(userId,tag)
        }
        val resultMakeTag = repositoryTag.makeTag(userId)
        return if(resultMakeTag == null) ServiceResponse.unexpectedError()
                else insert(userId,tag)
    }

    private fun insert(userId: String, tag: TagRequest) : ServiceResponse<Any> {
        val result = repositoryTag.insertTag(userId,tag)
        return if(result == null) ServiceResponse.unexpectedError()
        else ServiceResponse.Success(Any())
    }

    override fun updateTag(userId: String, tag: TagRequest): ServiceResponse<Any> {
        val tagCollection = repositoryTag.getTag(userId)
        return if(tagCollection == null) ServiceResponse.Error(10,"No user found")
                else {
                    val result = repositoryTag.updateTag(userId,tag)
                    if(result == null) ServiceResponse.unexpectedError()
                    else ServiceResponse.Success(Any())
                }
    }

    override fun getTag(userId: String): ServiceResponse<List<TagDB>> {
        val tagsCollection = repositoryTag.getTag(userId)
        return if(tagsCollection != null) ServiceResponse.Success(tagsCollection.tags)
                else ServiceResponse.Error(10,"No user found")
    }
}