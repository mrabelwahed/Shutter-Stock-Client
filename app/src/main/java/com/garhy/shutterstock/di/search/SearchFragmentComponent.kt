package com.garhy.shutterstock.di.search

import com.garhy.shutterstock.di.base.AppComponent
import com.garhy.shutterstock.di.scopes.FragmentScope
import com.garhy.shutterstock.view.SearchFragment
import dagger.Component

@FragmentScope
@Component(dependencies = [AppComponent::class], modules = [SearchFragmentModule::class])
interface SearchFragmentComponent {
    fun inject(searchFragment: SearchFragment)
}