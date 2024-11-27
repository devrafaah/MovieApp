package com.example.movieapp.data.interceptor

import com.example.movieapp.util.Constants
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

class LanguageInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl : HttpUrl = originalRequest.url

        val url = originalUrl.newBuilder()
            .addQueryParameter(LANGUAGE, Constants.Movie.LANGUAGE_PORTUGUESE)
            .build()

        val requestBuilder = originalRequest.newBuilder().url(url)
        val request = requestBuilder.build()

        return chain.proceed(request)

    }

    companion object {
        private const val LANGUAGE = "language"
    }
}