package com.alfonso.routing

import com.alfonso.model.database.UserDB
import com.mongodb.client.MongoDatabase
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import org.litote.kmongo.coroutine.CoroutineDatabase

fun Route.wordTestRouting() {
    val mongoDB : CoroutineDatabase by inject()
    route("/test") {
        get {
            print(mongoDB.name)
            val collection = mongoDB.getCollection<UserDB>("users")
            print(collection.namespace)
            call.respondText(mongoDB.name)
        }
    }
}