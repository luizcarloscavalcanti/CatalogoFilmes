package com.luizcarloscavalcanti.catalogodefilmes.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.luizcarloscavalcanti.catalogodefilmes.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val rule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun simpleClickOnPopularMovie() {
        Thread.sleep(5000) //Wait init animation
        onView(withId(R.id.rv_movie_list_action)).perform(click())
        onView(withId(R.id.movie_title)).check(matches(isDisplayed()))
    }
}