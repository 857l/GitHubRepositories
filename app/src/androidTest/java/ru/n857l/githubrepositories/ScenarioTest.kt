package ru.n857l.githubrepositories

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.n857l.githubrepositories.authentication.AuthenticationPage
import ru.n857l.githubrepositories.authentication.ErrorDialogUi
import ru.n857l.githubrepositories.core.MainActivity
import ru.n857l.githubrepositories.details.DetailsPage
import ru.n857l.githubrepositories.repositories.RepositoriesPage

@RunWith(AndroidJUnit4::class)
class ScenarioTest {

    @get:Rule
    val scenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun case1() {
        val authenticationPage = AuthenticationPage()

        scenarioRule.doWithRecreate {
            authenticationPage.assertInitialState("")
        }

        authenticationPage.addInput("bad_token!")

        scenarioRule.doWithRecreate {
            authenticationPage.assertWrongInputState()
        }

        authenticationPage.removeInputLastLetter()

        scenarioRule.doWithRecreate {
            authenticationPage.assertSuccessInputState()
        }

        authenticationPage.clickSignIn()

        scenarioRule.doWithRecreate {
            authenticationPage.assertLoadState()
        }

        val dialog = ErrorDialogUi()
        scenarioRule.doWithRecreate {
            dialog.assertVisible()
            dialog.assertMessage("Unauthorized — invalid token")
        }
    }

    @Test
    fun case2() {
        val authenticationPage = AuthenticationPage()

        scenarioRule.doWithRecreate {
            authenticationPage.assertInitialState("")
        }

        authenticationPage.addInput("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")

        scenarioRule.doWithRecreate {
            authenticationPage.assertSuccessInputState()
        }

        authenticationPage.clickSignIn()
        scenarioRule.doWithRecreate {
            authenticationPage.assertLoadState()
        }

        onView(isRoot()).perform(
            waitForView(withId(R.id.repositoriesContainer), 5000)
        )

        val repositoriesPage = RepositoriesPage()

        scenarioRule.doWithRecreate {
            repositoriesPage.assertVisible()
            repositoriesPage.assertNotEmpty()
        }

        repositoriesPage.clickOn(1)

        val detailsPage = DetailsPage()

        scenarioRule.doWithRecreate {
            detailsPage.assertVisible()
        }

        detailsPage.assertInitialState()
    }

    private fun ActivityScenarioRule<*>.doWithRecreate(block: () -> Unit) {
        block.invoke()
        scenario.recreate()
        block.invoke()
    }
}