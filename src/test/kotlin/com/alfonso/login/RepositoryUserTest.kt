package com.alfonso.login

import com.alfonso.general.repository.mongodb.MongoDB
import com.alfonso.login.model.database.TokenDB
import com.alfonso.login.model.database.UserDB
import com.alfonso.login.model.database.getTestUserDB
import com.alfonso.login.repository.mongodb.RepositoryUserImp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.bson.Document
import org.junit.Before
import org.junit.Test
import org.litote.kmongo.coroutine.CoroutineDatabase
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@ExperimentalCoroutinesApi
class RepositoryUserTest {
    private lateinit var database : CoroutineDatabase
    private lateinit var repositoryUser : RepositoryUserImp

    @Before
    fun initDataBase() {
        database = MongoDB.getDataBase("mongodb+srv://dbBooks:6ww4WyAEmKKSEtW@clusterbooks.igqbl.mongodb.net/Vocabulary?retryWrites=true&w=majority","Vocabulary-test")
        repositoryUser = RepositoryUserImp(database)
    }

    private suspend fun cleanUsersCollection() {
        val collection = database.getCollection<UserDB>("users")
        collection.deleteMany(Document())
    }

    @Test
    fun getUserByProviderNoUserNull() = runBlocking  {
        val result = repositoryUser.getUser("jjjjj","google")
        assertNull(result)
        cleanUsersCollection()
    }

    @Test
    fun getUserByProviderNoNull() = runBlocking {
        val resultUserDB = insertUserDB(repositoryUser)
        val resultGetUser = repositoryUser.getUser(resultUserDB!!.userId, resultUserDB.provider)
        assertEquals(resultUserDB,resultGetUser)
        cleanUsersCollection()
    }

    @Test
    fun getUserByIdNull() = runBlocking {
        val resultUserDB = repositoryUser.getUser("1")
        assertNull(resultUserDB)
        cleanUsersCollection()
    }

    @Test
    fun getUserByIdNoNull() = runBlocking {
        val resultUserDB = insertUserDB(repositoryUser)
        val resultGetUser = repositoryUser.getUser(resultUserDB!!._id!!)
        assertEquals(resultUserDB,resultGetUser)
        cleanUsersCollection()
    }


    @Test
    fun insertUserNoNull() = runBlocking {
        val userDB = UserDB.getTestUserDB()
        userDB._id = null
        val resultUserDB = repositoryUser.insertUser(userDB)
        assertNotNull(resultUserDB)
        assertEquals(userDB,resultUserDB)
        cleanUsersCollection()
    }

    @Test
    fun updateUserTest() = runBlocking {
        val userInserted = insertUserDB(repositoryUser)
        assertNotNull(userInserted)
        assertEquals(true, userInserted.tokens.isEmpty())
        userInserted.tokens.add(TokenDB("token", Date()))
        val number = repositoryUser.updateUser(userInserted)
        assertEquals(1, number)
        val getUserDB = repositoryUser.getUser(userInserted._id!!)
        assertNotNull(getUserDB)
        assertEquals(false,getUserDB.tokens.isEmpty())
        cleanUsersCollection()
    }

    private suspend fun insertUserDB(repositoryUser : RepositoryUserImp) : UserDB? {
        val userDB = UserDB.getTestUserDB()
        userDB._id = null
        return repositoryUser.insertUser(userDB)
    }
}