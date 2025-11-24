package ru.n857l.githubrepositories.repositories

import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Description
import org.hamcrest.Matcher
import ru.n857l.githubrepositories.R


class RepositoriesPage {
    private val containerIdMatcher: Matcher<View> =
        withParent(withId(R.id.repositoriesContainer))
    private val containerClassTypeMatcher: Matcher<View> =
        withParent(isAssignableFrom(LinearLayout::class.java))

    private val root = onView(
        allOf(
            withId(R.id.repositoriesContainer),
            isAssignableFrom(LinearLayout::class.java)
        )
    )

    private val recycler: ViewInteraction =
        onView(withId(R.id.repositories_list))

    fun assertNotEmpty() {
        recycler.check(matches(not(recyclerViewItemCountMatcher(0))))
    }

    fun assertVisible() {
        root.check(matches(isDisplayed()))
    }

    fun clickOn(position: Int) {
        recycler.perform(clickItemAt(position))
    }
}

fun recyclerViewItemCountMatcher(expectedCount: Int): Matcher<View> =
    object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {

        override fun describeTo(description: Description) {
            description.appendText("RecyclerView with item count: $expectedCount")
        }

        override fun matchesSafely(recyclerView: RecyclerView): Boolean {
            return recyclerView.adapter?.itemCount == expectedCount
        }
    }

fun clickItemAt(position: Int): ViewAction =
    object : ViewAction {

        override fun getConstraints(): Matcher<View> =
            isAssignableFrom(RecyclerView::class.java)

        override fun getDescription(): String =
            "Click on RecyclerView item at position $position"

        override fun perform(uiController: UiController, view: View) {
            val recycler = view as RecyclerView
            val holder = recycler.findViewHolderForAdapterPosition(position)
                ?: throw PerformException.Builder()
                    .withCause(Throwable("No ViewHolder at pos $position"))
                    .build()

            holder.itemView.performClick()
        }
    }