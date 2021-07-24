package com.alfonso.repository.mongodb

import com.alfonso.model.database.TagCollectionDB
import com.alfonso.model.database.TagDB
import com.alfonso.model.request.TagRequest
import com.alfonso.repository.IRepositoryTag
import com.mongodb.client.model.Updates
import java.lang.Exception
import org.litote.kmongo.eq
import org.litote.kmongo.and
import org.litote.kmongo.coroutine.CoroutineDatabase

class RepositoryTagImp(private val dataBase : CoroutineDatabase) : IRepositoryTag {
    private val TAG_API_COLLECTION = "tags"

    override suspend fun insertTag(userId: String, tag : TagRequest): TagCollectionDB? {
        val collection = getCollection()
        val update = Updates.push("tags",TagDB(tag.name,tag.color))
        return collection.findOneAndUpdate(TagCollectionDB::userId eq userId,update)
    }

    override suspend fun updateTag(userId: String, tag: TagRequest): TagCollectionDB? {
        val collection = getCollection()
        val update = Updates.push("tags.$",TagDB(tag.name,tag.color))
        return collection.findOneAndUpdate(and(TagCollectionDB::userId eq userId,TagDB::name eq tag.name),update)
    }

    override suspend fun getTag(userId: String): TagCollectionDB? {
        val collection = getCollection()
        return collection.findOne(TagCollectionDB::userId eq userId)
    }

    override suspend fun makeTag(userId: String) : TagCollectionDB? {
        return try {
            val collection = getCollection()
            val tagCollectionDB = TagCollectionDB(null,userId, ArrayList())
            collection.insertOne(tagCollectionDB)
            tagCollectionDB
        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }
    }

    private fun getCollection() = dataBase.getCollection<TagCollectionDB>(TAG_API_COLLECTION)
}
