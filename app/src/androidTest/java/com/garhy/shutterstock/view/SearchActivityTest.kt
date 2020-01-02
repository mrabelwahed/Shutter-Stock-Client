package com.garhy.shutterstock.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.garhy.shutterstock.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class SearchActivityTest{
    @get: Rule
    var rule =  ActivityScenarioRule<SearchActivity>(SearchActivity::class.java)

    @Test
    fun test_fragment_is_displayed(){
        onView(withId(R.id.fragment_container_view)).check(matches(isDisplayed()))
        onView(withId(R.id.images_rv)).check(matches(isDisplayed()))
    }

}