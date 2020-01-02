package com.garhy.shutterstock.di.base

import android.content.Context
import com.garhy.shutterstock.BuildConfig
import com.garhy.shutterstock.Constants
import com.garhy.shutterstock.network.AuthorizationInterceptor
import com.garhy.shutterstock.network.ShutterStockApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class AppModule constructor(val context: Context){
    @Singleton
    @Provides
    fun provideContext():Context{
        return context
    }
    @Singleton
    @Provides
    fun provideShutterStockApi(retrofit: Retrofit): ShutterStockApi {
        return retrofit.create(ShutterStockApi::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(AuthorizationInterceptor())
            .addInterceptor(HttpLoggingInterceptor().apply {
                level =
                    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            })
            .build()

    }
    @Singleton
    @Provides
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .baseUrl(Constants.BASE_URL)
            .build()
    }


}