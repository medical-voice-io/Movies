package io.android.movies.network

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * Добавляет API ключ в header запроса
 */
internal class HeaderInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader(HEADER_API_KEY, API_KEY)
                .build()
        )
    }

    private companion object {
        const val HEADER_API_KEY = "X-API-KEY"
        const val API_KEY = "ef630c73-b862-4dd3-80b2-9d15c2044e3a" // TODO: сюда вставить ключ API
    }
}