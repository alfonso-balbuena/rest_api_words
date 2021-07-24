package com.alfonso.routing

import com.mongodb.client.MongoDatabase
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.wordTestRouting() {
    val mongoDB : MongoDatabase by inject()
    route("/test") {
        get {
            print(mongoDB.name)
            val collection = mongoDB.getCollection("users")
            print(collection.namespace)
            call.respondText(mongoDB.name)
        }
    }
}