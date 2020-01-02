package com.garhy.shutterstock.repo.search_feature

import com.garhy.shutterstock.exceptions.AppException
import com.garhy.shutterstock.model.ApiResponse
import com.garhy.shutterstock.network.ApiResource
import com.garhy.shutterstock.network.ShutterStockApi
import com.garhy.shutterstock.repo.SearchImagesRepo
import com.garhy.shutterstock.repo.base.BaseUnitTest
import com.garhy.shutterstock.utils.RxImmediateSchedulerRule
import io.reactivex.Flowable
import io.reactivex.subscribers.TestSubscriber
import org.junit.*
import org.mockito.Mockito

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class SearchImagesRepoTest : BaseUnitTest() {
    override var isMockWebServerEnabled: Boolean = true


    private lateinit var repository: SearchImagesRepo

    private var shutterStockApi = Mockito.mock(ShutterStockApi::class.java)

    companion object {
        @get : ClassRule
        val schedulers = RxImmediateSchedulerRule()
    }

    @Before
    fun prepareRequiredData() {
        repository = SearchImagesRepo(shutterStockApi)

    }

    @Test
    fun test_successful_mapResponseToApiResource() {
        val testSubscriber = TestSubscriber<ApiResponse>()
        `mock a successful response and return flowable`().subscribe(testSubscriber)
        testSubscriber.assertComplete()
        val response = testSubscriber.values()[0]
        testSubscriber.assertNoErrors()
        val returnedSize = response.imageModels?.size
        Assert.assertEquals("images size should be only 20 in this test case", returnedSize, 20)
    }

    @Test
    fun test_failed_mapResponseToApiResource() {
        val articlesListResponse = `mock failure response with an unexpected app exception`()
        val apiResource = repository.mapResponseToApiResource(articlesListResponse)
        Assert.assertEquals(
            "Status should be Error",
            apiResource.status,
            ApiResource.Status.ERROR_OCCURRED
        )
        Assert.assertEquals("data should be null", apiResource.data, null)
    }



    @Test
    fun `test handle error from the api`() {
        val response = repository.onError(AppException())
        Assert.assertNotNull(response.appException)
        Assert.assertEquals(
            "exception error code is not the same ",
            response.appException?.errorCode, AppException.UNEXPECTED_ERROR
        )

    }


    private fun `mock failure response with an unexpected app exception`(): ApiResponse {
        val apiResponse = ApiResponse()
        apiResponse.appException = AppException()
        return apiResponse
    }

    private fun `mock a successful response and return flowable`(): Flowable<ApiResponse> {
        val endPointApis = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(mockWebServer.url("/").toString())
            .build().create(ShutterStockApi::class.java)

        mockHttpResponse("get_images_successful_response", HttpURLConnection.HTTP_OK)
        return endPointApis.getImages("", 1, 20)
    }
}
