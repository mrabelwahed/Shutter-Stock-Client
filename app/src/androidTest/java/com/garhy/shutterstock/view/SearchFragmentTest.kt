package com.garhy.shutterstock.view

import android.content.res.Resources
import android.view.KeyEvent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingPolicies
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.garhy.shutterstock.Constants.Companion.ITEMS_PER_PAGE
import com.garhy.shutterstock.R
import com.garhy.shutterstock.view.adapter.ImagesRecyclerViewAdapter
import org.hamcrest.Matchers
import org.hamcrest.core.IsNot.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeUnit


class SearchFragmentTest {
    private var mIdlingResource: IdlingResource? = null


    @get: Rule
    var rule = ActivityScenarioRule<SearchActivity>(SearchActivity::class.java)

    @Before
    fun registerIdlingResource() {

        rule.scenario.onActivity {
            mIdlingResource =
                (it.supportFragmentManager.fragments[0] as SearchFragment).getIdlingResource()
            IdlingRegistry.getInstance().register(mIdlingResource)
        }
        IdlingPolicies.setIdlingResourceTimeout(40, TimeUnit.SECONDS)

    }

    @Test
    fun test_open_search_fragment() {
        onView(withId(R.id.loading_view))
            .check(matches(not(isDisplayed())))

        onView(withId(R.id.images_rv))
            .check(matches(isDisplayed()))
    }


    @Test
    fun test_empty_view_when_no_data_found() {
        onView(
            withId(
                Resources.getSystem().getIdentifier(
                    "search_src_text",
                    "id", "android"
                )
            )
        ).perform(clearText(), typeText("asdasdasdasdas"))
            .perform(pressKey(KeyEvent.KEYCODE_ENTER))
        onView(withId(R.id.loading_view))
            .check(matches(not(isDisplayed())))
        onView(withId(R.id.empty_view))
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_checkRecyclerViewItemsCount() {
        onView(withId(R.id.images_rv))
            .check { view, noViewFoundException ->
                val recyclerView = view as RecyclerView
                assertThat(
                    "RecyclerView item count",
                    recyclerView.adapter?.itemCount,
                    Matchers.equalTo(ITEMS_PER_PAGE)
                )
            }
    }

    @Test
    fun test_checkRecyclerViewItemClick() {
        onView(withId(R.id.images_rv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ImagesRecyclerViewAdapter.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.details_image_view)).check(matches(isDisplayed()))
    }


    @After
    fun unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource)
        }
    }
}