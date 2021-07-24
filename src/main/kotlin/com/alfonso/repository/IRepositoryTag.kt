package com.alfonso.repository

import com.alfonso.model.database.TagCollectionDB
import com.alfonso.model.request.TagRequest

interface IRepositoryTag {
    suspend fun insertTag(userId: String, tag : TagRequest) : TagCollectionDB?
    suspend fun updateTag(userId: String, tag: TagRequest) : TagCollectionDB?
    suspend fun getTag(userId: String) : TagCollectionDB?
    suspend fun makeTag(userId: String) : TagCollectionDB?
}