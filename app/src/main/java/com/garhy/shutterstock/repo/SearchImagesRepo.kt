package com.garhy.shutterstock.repo

import com.garhy.shutterstock.exceptions.AppException
import com.garhy.shutterstock.model.ApiResponse
import com.garhy.shutterstock.network.ApiResource
import com.garhy.shutterstock.network.ShutterStockApi
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

open class SearchImagesRepo
@Inject constructor(private var shutterStockApi: ShutterStockApi) {

    fun fetchData(query: String?, page: Int, perPage: Int): Flowable<ApiResource<ApiResponse>> {
        return shutterStockApi.getImages(query, page, perPage)
            .onErrorReturn { t: Throwable -> onError(t) }
            .map { apiResponse -> mapResponseToApiResource(apiResponse) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }


    fun mapResponseToApiResource(apiResponse: ApiResponse): ApiResource<ApiResponse> {
        return apiResponse.appException?.let {
            ApiResource.error(it, null) as ApiResource<ApiResponse>
        } ?: ApiResource.dataLoaded(apiResponse) as ApiResource<ApiResponse>
    }

    fun onError(t: Throwable): ApiResponse {
        val errorCode = when (t) {
            is HttpException -> t.code()
            is SocketTimeoutException -> AppException.SOCKET_TIME_OUT_EXCEPTION
            is UnknownHostException -> AppException.NO_INTERNET_CONNECTION
            else -> AppException.UNEXPECTED_ERROR
        }
        return ApiResponse(appException = AppException(errorCode))
    }

}