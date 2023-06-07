package com.example.rewasteappmd.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.example.rewasteappmd.BuildConfig
import com.example.rewasteappmd.network.NetworkService
import com.example.rewasteappmd.network.Repository
import com.example.rewasteappmd.network.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiConfig {

    @Provides
    @Singleton
    fun providesBaseUrl(): String {
        return BuildConfig.BASE_URL
    }

    @Provides
    @Singleton
    fun providesChuckerCollector(
        @ApplicationContext ctx: Context
    ): ChuckerCollector {
        return ChuckerCollector(
            context = ctx,
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_DAY
        )
    }

    @Provides
    @Singleton
    fun providesChuckerInterceptor(
        @ApplicationContext ctx: Context,
        collector: ChuckerCollector
    ): ChuckerInterceptor {
        return ChuckerInterceptor(
            context = ctx,
            collector = collector,
            maxContentLength = 250000L,
            alwaysReadResponseBody = true
        )
    }

    @Provides
    @Singleton
    fun providesHttpClient(
        chuckerInterceptor: ChuckerInterceptor,
        @ApplicationContext ctx: Context
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .callTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(chuckerInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun providesGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun providesNetworkService(
        baseUrl: String,
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): NetworkService {
        return retrofit.create(NetworkService::class.java)
    }

    @Provides
    @Singleton
    fun providesRepository(
        networkService: NetworkService
    ): Repository {
        return RepositoryImpl(networkService)
    }

}