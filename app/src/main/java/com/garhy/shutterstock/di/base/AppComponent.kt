package com.garhy.shutterstock.di.base

import android.content.Context
import com.garhy.shutterstock.network.ShutterStockApi
import dagger.Component
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class]
)
interface AppComponent{
    fun getContext(): Context
    fun getShutterApi(): ShutterStockApi
    fun getOkHttpClient(): OkHttpClient
    fun getRetrofit(): Retrofit
}