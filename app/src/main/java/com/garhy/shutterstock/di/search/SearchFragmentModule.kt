package com.garhy.shutterstock.di.search

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.garhy.shutterstock.di.scopes.FragmentScope
import com.garhy.shutterstock.view.SearchFragment
import com.garhy.shutterstock.view.adapter.ImagesRecyclerViewAdapter
import com.garhy.shutterstock.viewmodel.SearchViewModel
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides


@Module
class SearchFragmentModule {
    @FragmentScope
    @Provides
    fun provideImageDownloader(): Picasso {
        return Picasso.get()
    }
    @FragmentScope
    @Provides
    fun provideImagesRecyclerViewAdapter(picasso: Picasso, context:Context): ImagesRecyclerViewAdapter {
        return ImagesRecyclerViewAdapter(context,  picasso)
    }

    @FragmentScope
    @Provides
    fun provideSearchViewModel(searchFragment: SearchFragment): ViewModel {
        return ViewModelProvider(searchFragment as ViewModelStoreOwner).get(SearchViewModel::class.java)
    }

}