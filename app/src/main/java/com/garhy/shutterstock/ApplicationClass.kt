package com.garhy.shutterstock

import android.app.Application
import com.garhy.shutterstock.di.base.AppComponent
import com.garhy.shutterstock.di.base.AppModule
import com.garhy.shutterstock.di.base.DaggerAppComponent

class ApplicationClass : Application() {
    lateinit var appComponent : AppComponent


    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }


}