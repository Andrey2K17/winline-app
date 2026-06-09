package ru.grinin.winlineapp.data.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request = original.newBuilder()
            .header("x-access-token", "winlinebyDev")
            .header("x-platform", "android")
            .header("x-platform-v", "1.2.25")
            .header("x-app-version", "1.0.0") // можно добавить
            .method(original.method, original.body)
            .build()

        return chain.proceed(request)
    }
}