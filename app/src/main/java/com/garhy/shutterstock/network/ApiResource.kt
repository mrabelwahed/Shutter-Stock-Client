package com.garhy.shutterstock.network

import com.garhy.shutterstock.exceptions.AppException

class ApiResource<T> constructor(

     var status : Status,
     var data: Any?,
     var exception : AppException?
){
    companion object {
        fun dataLoaded(data: Any?): ApiResource<Any> {
            return ApiResource(Status.LOADED_SUCCESSFULLY, data, null)
        }

        fun error(exception: AppException, data: Any?): ApiResource<Any> {
            return ApiResource(Status.ERROR_OCCURRED, data, exception)
        }

        fun loading(data: Any?): ApiResource<Any> {
            return ApiResource(Status.LOADING, data, null)
        }
    }

    enum class Status{
        LOADING, LOADED_SUCCESSFULLY, ERROR_OCCURRED
    }
}