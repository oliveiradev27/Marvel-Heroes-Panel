package br.espartano.marvelheroescatalog.ui.activities

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import br.espartano.marvelheroescatalog.R
import br.espartano.marvelheroescatalog.di.testErrorModule
import br.espartano.marvelheroescatalog.di.testModule
import org.koin.core.context.loadKoinModules

object MainActivityRobotsConstants {
    const val HERO_NAME = "Espartano"
}

class MainActivityGiven {

    fun initializeActivity() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    fun initializeSucessDependencies() {
        loadKoinModules(testModule)
    }

    fun initializeErrorDependencies() {
        loadKoinModules(testErrorModule)
    }
}

class MainActivityWhen {

}

class MainActivityThen {

    fun validateTitle(title : String) {
        onView(withId(R.id.tv_hero_name))
            .check(matches(withText(title)))
    }

    fun validateVisibilityDialogError() {
        onView(withText(R.string.system_body_error))
            .check(matches(isDisplayed()))
    }
}