package com.alfonso.repository

import com.alfonso.model.database.TagCollectionDB
import com.alfonso.model.database.TagDB
import com.alfonso.model.request.TagRequest

interface IRepositoryTag {
    fun insertTag(userId: String, tag : TagRequest) : TagCollectionDB?
    fun updateTag(userId: String, tag: TagRequest) : TagCollectionDB?
    fun getTag(userId: String) : TagCollectionDB?
    fun makeTag(userId: String) : TagCollectionDB?
}