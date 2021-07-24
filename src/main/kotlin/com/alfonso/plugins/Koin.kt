package com.alfonso.plugins

import com.alfonso.repository.IRepositoryApiKey
import com.alfonso.repository.IRepositoryTag
import com.alfonso.repository.IRepositoryUser
import com.alfonso.repository.mongodb.MongoDB
import com.alfonso.repository.mongodb.RepositoryApiKeyImp
import com.alfonso.repository.mongodb.RepositoryTagImp
import com.alfonso.repository.mongodb.RepositoryUserImp
import com.alfonso.service.*
import com.alfonso.service.imp.*
import io.ktor.application.*
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.modules

val moduleApp : (String,String) -> Module =   { connectionString, database ->
    module {
        single<IRepositoryApiKey> { RepositoryApiKeyImp(get()) }
        single { MongoDB.getDataBase(connectionString,database) }
    }
}

val moduleApiKeyAppProduction = module {
    single<AuthService> { AuthServiceImp(get(),get()) }
    single<ApiKeyService> { ApiKeyServiceImp() }
    single<IRepositoryApiKey> { RepositoryApiKeyImp(get()) }
}

val moduleLoginAppProduction = module {
    single<LoginService> { LoginServiceImp(get(),get()) }
    single<TokenService> { TokenServiceImp() }
    single<IRepositoryUser> { RepositoryUserImp(get()) }
}

val moduleTagAppProduction = module {
    single<TagService> { TagServiceImp(get())}
    single<IRepositoryTag> { RepositoryTagImp(get())}
}
fun Application.configureKoin() {
    val env = environment.config.propertyOrNull("ktor.environment")?.getString()
    val (conn,db) = getDataBaseConf(env)
    install(Koin) {
        modules(moduleApp(conn,db))
        modules(moduleApiKeyAppProduction)
        modules(moduleLoginAppProduction)
        modules(moduleTagAppProduction)
    }
}

fun Application.getDataBaseConf(env: String?) : Pair<String,String> {
    env?.let {
        val type = if(env == "DEV") "development" else "production"
        val connection = environment.config.property("ktor.database.${type}.connectionString")
        val databaseS = environment.config.property("ktor.database.${type}.database")
        return Pair(connection.getString(),databaseS.getString())
    }
    return  Pair("","")
}