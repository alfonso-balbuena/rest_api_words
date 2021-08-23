package com.alfonso.tag

import com.alfonso.general.repository.mongodb.MongoDB
import com.alfonso.tag.model.database.TagDB
import com.alfonso.tag.model.request.TagRequest
import com.alfonso.tag.repository.mongodb.RepositoryTagImp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.bson.Document
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@ExperimentalCoroutinesApi
class RepositoryTagTest {
    private lateinit var repositoryTag : RepositoryTagImp
    private lateinit var database : CoroutineDatabase

    @Before
    fun initDataBase() {
        database = MongoDB.getDataBase("mongodb+srv://dbBooks:6ww4WyAEmKKSEtW@clusterbooks.igqbl.mongodb.net/Vocabulary?retryWrites=true&w=majority","Vocabulary-test")
        repositoryTag = RepositoryTagImp(database)
    }

    private suspend fun cleanTagsCollection() {
        val collection = database.getCollection<TagDB>("tags")
        val result = collection.deleteMany(Document())
    }

    @Test
    fun `Insert tag get the same tag`() = runBlocking {
        cleanTagsCollection()
        val tag = repositoryTag.makeTag("1")
        assertNotNull(tag?._id)
        val tagUser = repositoryTag.getTag("1")
        assertNotNull(tagUser)
        assertEquals(tag,tagUser)
        cleanTagsCollection()
    }

    @Test
    fun `No find id return null`() : Unit = runBlocking {
        cleanTagsCollection()
        val tag = repositoryTag.getTag("1")
        assertNull(tag)
    }

    @Test
    fun `Insert a tag in the list of tags`() = runBlocking {
        cleanTagsCollection()
        val tag = repositoryTag.makeTag("1")
        assertNotNull(tag?._id)
        val tagRequest = TagRequest("english")
        val doc = repositoryTag.insertTag("1",tagRequest)
        assertNotNull(doc)
        assertEquals(1, doc.tags.size)
        cleanTagsCollection()
    }

    @Test
    fun `Try to insert tag and no find userId return null`() : Unit = runBlocking {
        cleanTagsCollection()
        val tagRequest = TagRequest("english")
        val doc = repositoryTag.insertTag("1",tagRequest)
        assertNull(doc)
    }

    @Test
    fun `Update a tag in the collection`() = runBlocking {
        cleanTagsCollection()
        val tag = repositoryTag.makeTag("1")
        assertNotNull(tag?._id)
        var tagRequest = TagRequest("english")
        val doc = repositoryTag.insertTag("1",tagRequest)
        assertNotNull(doc)
        assertEquals(1, doc.tags.size)
        assertNull(doc.tags[0].color)
        tagRequest = TagRequest("english",2318)
        val docUpdated = repositoryTag.updateTag("1",tagRequest)
        assertNotNull(docUpdated)
        assertEquals(1, docUpdated.tags.size)
        assertNotNull(docUpdated.tags[0].color)
        assertEquals(2318,docUpdated.tags[0].color)
        cleanTagsCollection()
    }

    @Test
    fun `Add two Tags with the same tag english and Update just one`() = runBlocking{
        cleanTagsCollection()
        val tag = repositoryTag.makeTag("1")
        val tag1 = repositoryTag.makeTag("2")
        var tagRequest = TagRequest("english")
        repositoryTag.insertTag("1",tagRequest)
        repositoryTag.insertTag("2",tagRequest)
        tagRequest = TagRequest("english",1)
        val docUpdated = repositoryTag.updateTag("1",tagRequest)
        assertNotNull(docUpdated)
        assertEquals("1",docUpdated.userId)
        assertEquals(1,docUpdated.tags.size)
        assertEquals("english",docUpdated.tags[0].name)
        assertNotNull(docUpdated.tags[0].color)
        assertEquals(1,docUpdated.tags[0].color)
        val getTag = repositoryTag.getTag("2")
        assertNotNull(getTag)
        assertNull(getTag.tags[0].color)
        cleanTagsCollection()
    }

    @Test
    fun `Try update a tag that is not in the collection`() = runBlocking {
        cleanTagsCollection()
        val tag = repositoryTag.makeTag("1")
        assertNotNull(tag?._id)
        val tagRequest = TagRequest("english",3242)
        val docUpdated = repositoryTag.updateTag("1",tagRequest)
        assertNull(docUpdated)
        cleanTagsCollection()
    }
}