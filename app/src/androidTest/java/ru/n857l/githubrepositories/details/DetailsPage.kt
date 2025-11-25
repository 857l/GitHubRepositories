package ru.n857l.githubrepositories.details

import android.view.View
import android.widget.LinearLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher
import ru.n857l.githubrepositories.ProgressUi
import ru.n857l.githubrepositories.R

class DetailsPage {
    private val containerIdMatcher: Matcher<View> =
        withParent(withId(R.id.detailsContainer))
    private val containerClassTypeMatcher: Matcher<View> =
        withParent(isAssignableFrom(LinearLayout::class.java))

    private val root = onView(
        allOf(
            withId(R.id.detailsContainer),
            isAssignableFrom(LinearLayout::class.java)
        )
    )

    private val linkUi = TextViewUi(
        id = R.id.link,
        containerIdMatcher = containerIdMatcher,
    )

    private val licenseUi = TextViewUi(
        id = R.id.license,
        containerIdMatcher = containerIdMatcher,
    )

    private val starsUi = TextViewUi(
        id = R.id.stars,
        containerIdMatcher = containerIdMatcher,
    )

    private val forksUi = TextViewUi(
        id = R.id.forks,
        containerIdMatcher = containerIdMatcher,
    )

    private val watchersUi = TextViewUi(
        id = R.id.watchers,
        containerIdMatcher = containerIdMatcher,
    )

    private val readmeUi = TextViewUi(
        id = R.id.readme,
        containerIdMatcher = containerIdMatcher,
    )

    private val progressUi = ProgressUi(
        containerIdMatcher = containerIdMatcher,
        classTypeMatcher = containerClassTypeMatcher
    )

    fun assertVisible() {
        root.check(matches(isDisplayed()))
    }

    fun assertInitialState() {
        linkUi.assertInitial()
        licenseUi.assertVisible()
        starsUi.assertInitial()
        forksUi.assertInitial()
        watchersUi.assertInitial()
        readmeUi.assertEmpty()
    }
}