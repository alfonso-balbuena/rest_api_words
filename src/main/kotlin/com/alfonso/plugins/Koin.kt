package com.alfonso.plugins

import com.alfonso.apikey.service.ApiKeyService
import com.alfonso.apikey.service.AuthService
import com.alfonso.apikey.service.imp.ApiKeyServiceImp
import com.alfonso.apikey.service.imp.AuthServiceImp
import com.alfonso.login.service.LoginService
import com.alfonso.login.service.TokenService
import com.alfonso.login.service.imp.LoginServiceImp
import com.alfonso.login.service.imp.TokenServiceImp
import com.alfonso.apikey.repository.IRepositoryApiKey
import com.alfonso.tag.repository.IRepositoryTag
import com.alfonso.login.repository.IRepositoryUser
import com.alfonso.general.repository.mongodb.MongoDB
import com.alfonso.apikey.repository.mongodb.RepositoryApiKeyImp
import com.alfonso.tag.repository.mongodb.RepositoryTagImp
import com.alfonso.login.repository.mongodb.RepositoryUserImp
import com.alfonso.tag.service.*
import com.alfonso.tag.service.imp.*
import io.ktor.application.*
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.ktor.ext.Koin

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
    single<IRepositoryTag> { RepositoryTagImp(get()) }
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