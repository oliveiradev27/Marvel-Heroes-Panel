package br.espartano.marvelheroescatalog.ui.activities

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import br.espartano.marvelheroescatalog.R
import br.espartano.marvelheroescatalog.repository.CharactersErrorTestRepository
import br.espartano.marvelheroescatalog.repository.CharactersRepository

object MainActivityRobotsConstants {
    const val HERO_NAME = "Espartano"
    const val HERO_DESCRIPTION = "Overpower"
}

class MainActivityGiven {

    fun initializeActivity() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.moveToState(Lifecycle.State.RESUMED)
    }
}

class MainActivityThen {

    fun validateTitle(title: String) {
        onView(withId(R.id.tv_hero_name))
            .check(matches(withText(title)))
    }

    fun validateDescrition(description: String) {
        onView(withId(R.id.tv_hero_description))
            .check(matches(withText(description)))
    }

    fun validateVisibilityDialogError() {
        onView(withText(R.string.system_body_error))
            .check(matches(isDisplayed()))
    }
}
