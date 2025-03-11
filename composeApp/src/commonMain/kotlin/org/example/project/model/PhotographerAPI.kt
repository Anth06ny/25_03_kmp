package org.example.project.model

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

suspend fun main() {
    println(PhotographerAPI.loadPhotographers().joinToString(separator = "\n\n"))
}

object PhotographerAPI {
    private const val API_URL = "https://www.amonteiro.fr/api/photographers"

    //Déclaration du client
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true }, contentType = ContentType.Any)
        }
    }

    //GET
    suspend fun loadPhotographers(): List<Photographer> {
        return client.get(API_URL) {
//            headers {
//                append("Authorization", "Bearer YOUR_TOKEN")
//                append("Custom-Header", "CustomValue")
//            }
        }.body()
    }

    //Avec Flow
    fun getDataFlow() = flow<List<Photographer>> {
        emit(client.get(API_URL).body())
    }
}

@Serializable
data class Photographer(
    val id: Int,
    val stageName: String,
    val photoUrl: String,
    val story: String,
    val portfolio: List<String>
)

