package ru.n857l.githubrepositories

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.n857l.githubrepositories.authentication.AuthenticationPage
import ru.n857l.githubrepositories.core.MainActivity

@RunWith(AndroidJUnit4::class)
class ScenarioTest {

    @get:Rule
    val scenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun caseNumber1() {
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
    }

    private fun ActivityScenarioRule<*>.doWithRecreate(block: () -> Unit) {
        block.invoke()
        scenario.recreate()
        block.invoke()
    }
}