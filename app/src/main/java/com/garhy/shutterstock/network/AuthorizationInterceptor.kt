package com.garhy.shutterstock.network

import com.garhy.shutterstock.Constants
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
            .header(
                Constants.HEADER_AUTH,
                Credentials.basic(Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET))
        val request = builder.build()
        return chain.proceed(request)
    }
}