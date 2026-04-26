package com.example.lnm.payments.data.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MpesaNetworkModule {

    @Provides
    @Singleton
    @MpesaRetrofit
    fun provideMpesaRetrofit(
        client: OkHttpClient,
        json: Json
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://sandbox.safaricom.co.ke/")
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

    @Provides
    fun provideMpesaApi(
        @MpesaRetrofit retrofit: Retrofit
    ): MpesaApi = retrofit.create(MpesaApi::class.java)
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MpesaRetrofit