package ru.grinin.winlineapp.data.di

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import ru.grinin.winlineapp.BuildConfig
import ru.grinin.winlineapp.data.datasource.remote.ApiService
import ru.grinin.winlineapp.data.interceptors.AuthInterceptor
import ru.grinin.winlineapp.data.interceptors.LoggingInterceptor
import ru.grinin.winlineapp.data.socket.SocketService
import java.util.concurrent.TimeUnit

private const val CONNECT_TIMEOUT = 30L
private const val READ_TIMEOUT = 30L


val networkModule = module {

    single {
        Json {
            ignoreUnknownKeys = true
        }
    }

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
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(get<Json>().asConverterFactory("application/json".toMediaType()))
            .build()

    }

    single<ApiService> { get<Retrofit>().create(ApiService::class.java) }

    single { SocketService() }

}