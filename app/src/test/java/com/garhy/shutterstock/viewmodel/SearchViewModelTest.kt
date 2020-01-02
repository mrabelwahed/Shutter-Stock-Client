package com.garhy.shutterstock.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.garhy.shutterstock.exceptions.AppException
import com.garhy.shutterstock.model.ApiResponse
import com.garhy.shutterstock.network.ApiResource
import com.garhy.shutterstock.repo.SearchImagesRepo
import com.garhy.shutterstock.utils.RxImmediateSchedulerRule
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito


class SearchViewModelTest {

    private lateinit var searchViewModel: SearchViewModel
    private var imagesRepo = Mockito.mock(SearchImagesRepo::class.java)

    @Mock
    var observer = Mockito.mock( Observer::class.java)



    companion object {

        @get : ClassRule
        val schedulers = RxImmediateSchedulerRule()



    }
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()



    @Before
    fun setup() {
        searchViewModel = SearchViewModel(imagesRepo)
    }


    @Test
    fun `test live data observation for exception scenario`() {

        searchViewModel.imagesLiveData.observeForever((observer as Observer<in ApiResource<ApiResponse>>?)!!)

        searchViewModel.imagesLiveData.postValue(ApiResource(ApiResource.Status.LOADING, null, null))

        assert(searchViewModel.imagesLiveData.value?.status == ApiResource.Status.LOADING)

        searchViewModel.imagesLiveData.value = ApiResource.error(AppException(),null) as ApiResource<ApiResponse>

        assert(searchViewModel.imagesLiveData.value?.status != ApiResource.Status.LOADING)

        assert(searchViewModel.imagesLiveData.value?.status == ApiResource.Status.ERROR_OCCURRED)

        assertNotNull(searchViewModel.imagesLiveData.value?.exception)


    }



    @Test
    fun `test live data observation`() {

        searchViewModel.imagesLiveData.observeForever((observer as Observer<in ApiResource<ApiResponse>>?)!!)

        searchViewModel.imagesLiveData.postValue(ApiResource(ApiResource.Status.LOADING, null, null))

        assert(searchViewModel.imagesLiveData.value?.status == ApiResource.Status.LOADING)

        searchViewModel.imagesLiveData.value = ApiResource.dataLoaded(mockResponse()) as ApiResource<ApiResponse>

        assert(searchViewModel.imagesLiveData.value?.status != ApiResource.Status.LOADING)

        assert(searchViewModel.imagesLiveData.value?.status == ApiResource.Status.LOADED_SUCCESSFULLY)

        assertNotNull(searchViewModel.imagesLiveData.value?.data)


    }

    private fun mockResponse(): ApiResource<ApiResponse> {
        val apiResponse = ApiResponse()
        apiResponse.imageModels = ArrayList()
        apiResponse.page = 1
        apiResponse.per_page = 20
        return ApiResource(ApiResource.Status.LOADED_SUCCESSFULLY, apiResponse, null)
    }
}