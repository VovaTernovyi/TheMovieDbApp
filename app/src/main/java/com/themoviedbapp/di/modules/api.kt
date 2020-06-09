package com.themoviedbapp.di.modules

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.GsonBuilder
import com.themoviedbapp.BuildConfig
import com.themoviedbapp.extension.onNotReleaseBuild
import com.themoviedbapp.model.network.contract.MovieContract
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val KoinApiModule = module {
    single(definition = { getOkHttpClient() })
    single { getRetrofit(get()) }

    factory { provideMovie(get()) }
}

fun provideMovie(retrofit: Retrofit): MovieContract = retrofit.create(MovieContract::class.java)

fun getOkHttpClient(): OkHttpClient =
    with(OkHttpClient.Builder()) {
        followRedirects(false)
        connectTimeout(20, java.util.concurrent.TimeUnit.SECONDS) // default: 10 seconds
        readTimeout(20, java.util.concurrent.TimeUnit.SECONDS) // default: 10 seconds
        writeTimeout(20, java.util.concurrent.TimeUnit.SECONDS) // default: 10 seconds
        addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val original: Request = chain.request()
                val originalHttpUrl = original.url

                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter(
                        "api_key",
                        "0d988a26e2a9d227ba4785175db9a891"
                    )
                    .build()

                val requestBuilder: Request.Builder = original.newBuilder().url(url)

                val request: Request = requestBuilder.build()
                return chain.proceed(request)
            }

        })
        onNotReleaseBuild {
            networkInterceptors().add(StethoInterceptor())
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }
        return build()
    }

fun getRetrofit(client: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .client(client)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder()
                    .setLenient()
                    .create()
            )
        )
        .build()