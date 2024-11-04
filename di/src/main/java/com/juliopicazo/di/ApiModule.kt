package com.juliopicazo.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.juliopicazo.data.networking.GameCatalogApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(loggingInterceptor)
                }
            }.build()

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor()
            .setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val gson: Gson = GsonBuilder().setLenient().create()

        return Retrofit
            .Builder()
            .baseUrl(com.juliopicazo.data.BuildConfig.GAME_CATALOG_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): GameCatalogApi = retrofit.create(GameCatalogApi::class.java)
}
