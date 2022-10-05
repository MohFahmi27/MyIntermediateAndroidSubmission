package com.mohfahmi.storyapp.auth

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mohfahmi.storyapp.core.utils.EspressoIdlingResource
import com.mohfahmi.storyapp.home.HomeActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class AuthActivityTest {

    @get:Rule
    val activity = ActivityScenarioRule(AuthActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun login_success() {
        Intents.init()
        onView(withId(R.id.ed_login_email)).perform(typeText("mohammadfahmi417@gmail.com"),
            closeSoftKeyboard())
        onView(withId(R.id.ed_login_password)).perform(typeText("1234566"), closeSoftKeyboard())
        onView(withId(R.id.btn_login)).perform(click())
        intended(hasComponent(HomeActivity::class.java.name))
    }

    @Test
    fun login_failed() {
        onView(withId(R.id.ed_login_email)).perform(
            typeText("mohammadfahmi417@gmail.com"),
            closeSoftKeyboard(),
        )
        onView(withId(R.id.ed_login_password)).perform(
            typeText("123456q6"),
            closeSoftKeyboard()
        )
        onView(withId(R.id.btn_login)).perform(click())
        onView(withText("Whoops")).check(matches(isDisplayed())) // check if error dialog shown up
    }

}