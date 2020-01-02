package com.garhy.shutterstock.network

import com.garhy.shutterstock.Constants.Companion.PAGE_PARAM
import com.garhy.shutterstock.Constants.Companion.PER_PAGE_PARAM
import com.garhy.shutterstock.Constants.Companion.QUERY_PARAM
import com.garhy.shutterstock.Constants.Companion.SEARCH_API
import com.garhy.shutterstock.model.ApiResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface ShutterStockApi {
@GET(SEARCH_API)
fun getImages (@Query(QUERY_PARAM) query: String?, @Query(PAGE_PARAM) page : Int,
               @Query(PER_PAGE_PARAM) per_page : Int): Flowable<ApiResponse>
}