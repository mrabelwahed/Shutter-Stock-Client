package com.garhy.shutterstock.exceptions

import java.lang.Exception

class AppException @JvmOverloads constructor(var errorCode: Int = UNEXPECTED_ERROR) : Exception() {
    var enableRetryProcess = false
    private var errorMessage: String? = null

    init {
        errorMessage = this.message
    }

    companion object {
        const val NO_INTERNET_CONNECTION = 1
        const val UNEXPECTED_ERROR = 0
        private const val NOT_AUTHORIZED = 401
        private const val BAD_REQUEST = 400
        private const val INTERNAL_SERVER_ERROR = 500
        const val SOCKET_TIME_OUT_EXCEPTION = 2


        fun getErrorMessage(appException: AppException): String? {
            when (appException.errorCode) {
                NO_INTERNET_CONNECTION -> {
                    appException.enableRetryProcess = true
                    return "it seems that you aren't connected to working internet connection"
                }
                NOT_AUTHORIZED -> {
                    appException.enableRetryProcess = false
                    return "unfortunately the app is not authorized to call shutter api"
                }
                BAD_REQUEST -> {
                    appException.enableRetryProcess = false
                    return "it seems something went wrong"
                }
                INTERNAL_SERVER_ERROR -> {
                    appException.enableRetryProcess = true
                    return "Service currently unavailable, please try again"
                }
                SOCKET_TIME_OUT_EXCEPTION -> {
                    appException.enableRetryProcess = true
                    return "can't reach the server, please try again"
                }
                UNEXPECTED_ERROR -> {
                    appException.enableRetryProcess = false
                    return "it seems something went wrong"
                }
                else -> {
                    return appException.message
                }
            }
        }
    }

}