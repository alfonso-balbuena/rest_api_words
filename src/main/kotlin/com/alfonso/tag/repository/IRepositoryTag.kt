package com.alfonso.tag.repository

import com.alfonso.tag.model.database.TagCollectionDB
import com.alfonso.tag.model.request.TagRequest

interface IRepositoryTag {
    suspend fun insertTag(userId: String, tag : TagRequest) : TagCollectionDB?
    suspend fun updateTag(userId: String, tag: TagRequest) : TagCollectionDB?
    suspend fun getTag(userId: String) : TagCollectionDB?
    suspend fun makeTag(userId: String) : TagCollectionDB?
}