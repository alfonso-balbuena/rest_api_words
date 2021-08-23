package com.alfonso.tag.repository.fake

import com.alfonso.tag.model.database.TagCollectionDB
import com.alfonso.tag.model.request.TagRequest
import com.alfonso.tag.repository.IRepositoryTag

class RepositoryTagFake(var nullInsert : Boolean = false,var nullUpdate : Boolean = false,
                        var nullGet : Boolean = false,var nullMake : Boolean = false): IRepositoryTag {



    override suspend fun insertTag(userId: String, tag: TagRequest): TagCollectionDB? {
        TODO("Not yet implemented")
    }

    override suspend fun updateTag(userId: String, tag: TagRequest): TagCollectionDB? {
        TODO("Not yet implemented")
    }

    override suspend fun getTag(userId: String): TagCollectionDB? {
        TODO("Not yet implemented")
    }

    override suspend fun makeTag(userId: String): TagCollectionDB? {
        TODO("Not yet implemented")
    }
}