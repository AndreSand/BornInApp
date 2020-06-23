package com.kotlinmap.andres.mapapp



import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        val appCompatEditText = onView(
            allOf(
                withId(R.id.edLocationName),
                childAtPosition(
                    allOf(
                        withId(R.id.form_layout),
                        childAtPosition(
                            withClassName(`is`("android.support.constraint.ConstraintLayout")),
                            0
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatEditText.perform(replaceText("USA"), closeSoftKeyboard())

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.edLocationName), withText("USA"),
                childAtPosition(
                    allOf(
                        withId(R.id.form_layout),
                        childAtPosition(
                            withClassName(`is`("android.support.constraint.ConstraintLayout")),
                            0
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatEditText2.perform(pressImeActionButton())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}

