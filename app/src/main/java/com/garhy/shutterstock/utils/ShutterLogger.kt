package com.garhy.shutterstock.utils

import android.util.Log
import com.garhy.shutterstock.BuildConfig
import com.garhy.shutterstock.exceptions.AppException
import java.io.PrintWriter
import java.io.StringWriter


/**
 * @Class ShutterLogger is a Logger class helps to log messages and exceptions to the
 * logcat, and controlled by the debug mode and enable/disable logging in general
 *
 */
class ShutterLogger {
    companion object {
        private const val TAG = "X_SHUTTER_APP_X"
        private const val ENABLE_LOGGING = true
        fun log(message: String) {
            if (BuildConfig.DEBUG && ENABLE_LOGGING)
                Log.d(TAG, message)

        }


        fun log(exception: AppException) {
            log(exception.getStackTraceAsString())
        }
    }

}

/**
 * extract the stack trace of an exception into string
 */
fun AppException.getStackTraceAsString(): String {
    val stringWriter = StringWriter()
    val printerWriter = PrintWriter(stringWriter)
    printStackTrace(printerWriter)
    return stringWriter.toString()

}