package com.garhy.shutterstock.viewmodel

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.garhy.shutterstock.Constants.Companion.ITEMS_PER_PAGE
import com.garhy.shutterstock.model.ApiResponse
import com.garhy.shutterstock.network.ApiResource
import com.garhy.shutterstock.repo.SearchImagesRepo
import javax.inject.Inject

class SearchViewModel @Inject constructor(private var searchImagesRepo: SearchImagesRepo) :
    ViewModel() {

    var imagesLiveData: MediatorLiveData<ApiResource<ApiResponse>> =
        MediatorLiveData()

    fun fetchImages(query: String?, nextPage: Int) {
        imagesLiveData.postValue(ApiResource.loading(null) as ApiResource<ApiResponse>)
        val dataSource = LiveDataReactiveStreams.fromPublisher(
            searchImagesRepo.fetchData(query, nextPage, ITEMS_PER_PAGE)
        )

        imagesLiveData.addSource(dataSource) {
            imagesLiveData.value = it as ApiResource<ApiResponse>
            imagesLiveData.removeSource(dataSource)
        }
    }

}