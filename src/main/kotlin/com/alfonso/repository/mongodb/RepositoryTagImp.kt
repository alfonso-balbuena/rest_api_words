package com.alfonso.repository.mongodb

import com.alfonso.model.database.TagCollectionDB
import com.alfonso.model.database.TagDB
import com.alfonso.model.request.TagRequest
import com.alfonso.repository.IRepositoryTag
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.FindOneAndUpdateOptions
import com.mongodb.client.model.Updates
import io.ktor.utils.io.*
import org.litote.kmongo.getCollection
import java.lang.Exception
import org.litote.kmongo.eq
import org.litote.kmongo.and
import org.litote.kmongo.findOne

class RepositoryTagImp(private val dataBase : MongoDatabase) : IRepositoryTag {
    private val TAG_API_COLLECTION = "tags"

    override fun insertTag(userId: String, tag : TagRequest): TagCollectionDB? {
        val collection = getCollection()
        val update = Updates.push("tags",TagDB(tag.name,tag.color))
        return collection.findOneAndUpdate(TagCollectionDB::userId eq userId,update)
    }

    override fun updateTag(userId: String,tag: TagRequest): TagCollectionDB? {
        val collection = getCollection()
        val update = Updates.push("tags.$",TagDB(tag.name,tag.color))
        return collection.findOneAndUpdate(and(TagCollectionDB::userId eq userId,TagDB::name eq tag.name),update)
    }

    override fun getTag(userId: String): TagCollectionDB? {
        val collection = getCollection()
        return collection.findOne(TagCollectionDB::userId eq userId)
    }

    override fun makeTag(userId: String) : TagCollectionDB? {
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
