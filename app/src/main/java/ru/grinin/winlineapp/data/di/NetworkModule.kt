package ru.grinin.winlineapp.data.di

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import ru.grinin.winlineapp.data.datasource.remote.ApiService
import ru.grinin.winlineapp.data.interceptors.AuthInterceptor
import ru.grinin.winlineapp.data.interceptors.LoggingInterceptor
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://kz.stage.sxl.bet/"
private const val CONNECT_TIMEOUT = 30L
private const val READ_TIMEOUT = 30L

private val json = Json {
    ignoreUnknownKeys = true
}

val networkModule = module {

    single { AuthInterceptor() }
    single { LoggingInterceptor() }

    single<Retrofit> {
        val client = OkHttpClient.Builder()
            .addInterceptor(get<AuthInterceptor>())
            .addInterceptor(get<LoggingInterceptor>())
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .build()


        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

    }

    single<ApiService> { get<Retrofit>().create(ApiService::class.java) }

}