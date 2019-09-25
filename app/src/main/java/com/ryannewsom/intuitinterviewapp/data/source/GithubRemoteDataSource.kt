package com.ryannewsom.intuitinterviewapp.data.source

import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class GithubRemoteDataSource {
    protected val retrofit: Retrofit
        get() {
            val okHttpClient = OkHttpClient().newBuilder()
                .addInterceptor { chain ->
                    val request = chain.request()
                    val authedRequest: Request

                    authedRequest = request.newBuilder()
                        .addHeader(AUTHENTICATION_HEADER, GITHUB_API_KEY)
                        .build()

                    chain.proceed(authedRequest)
                }.build()

            return Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
        }

    companion object {
        const val AUTHENTICATION_HEADER = "Authorization"
        const val GITHUB_API_KEY = "token 621122f3fe0f1c3701bf0db326c8eb9fd2c2233a"
    }
}