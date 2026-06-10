package ru.grinin.winlineapp.data.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request = original.newBuilder()
            .header("x-access-token", ApiConstants.ACCESS_TOKEN)
            .header("x-platform", ApiConstants.PLATFORM)
            .header("x-platform-v", ApiConstants.PLATFORM_VERSION)
            .header("x-app-version", ApiConstants.APP_VERSION)
            .method(original.method, original.body)
            .build()

        return chain.proceed(request)
    }
}