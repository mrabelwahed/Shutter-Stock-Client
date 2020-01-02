package com.garhy.shutterstock

import com.garhy.shutterstock.repo.search_feature.SearchImagesRepoTest
import com.garhy.shutterstock.viewmodel.SearchViewModelTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    SearchViewModelTest::class,
    SearchImagesRepoTest::class
)
class SearchTestSuit