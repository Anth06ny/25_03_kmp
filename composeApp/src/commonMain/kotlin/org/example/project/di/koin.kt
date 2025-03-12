package org.example.project.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.example.project.MainViewModel
import org.example.project.model.PhotographerAPI
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

//Si besoin du contexte, pour le passer en paramètre
fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(apiModule, viewModelModule)
    }

// Version pour iOS et Desktop
fun initKoin() = initKoin {}

val apiModule = module {
    //Création d'un singleton pour le client HTTP
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true }, contentType = ContentType.Any)
            }
        }
    }

    //Création d'un singleton pour les repository. Get injectera les instances
    //single { PhotographerAPI(get()) }

    //Nouvelle version avec injection automatique des instances
    singleOf(::PhotographerAPI)
}

//Version spécifique au ViewModel
val viewModelModule = module {
    viewModelOf(::MainViewModel)
}