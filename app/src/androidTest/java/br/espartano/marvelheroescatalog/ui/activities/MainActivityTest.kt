package br.espartano.marvelheroescatalog.ui.activities

import androidx.test.ext.junit.runners.AndroidJUnit4
import br.espartano.marvelheroescatalog.di.testErrorModule
import br.espartano.marvelheroescatalog.di.testModule
import br.espartano.marvelheroescatalog.ui.activities.MainActivityRobotsConstants.HERO_NAME
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.unloadKoinModules

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private fun given(func: MainActivityGiven.() -> Unit) = MainActivityGiven().apply { func() }
    //private fun `when`(func: MainActivityWhen.() -> Unit) = MainActivityWhen().apply { func() }
    private fun then(func: MainActivityThen.() -> Unit) = MainActivityThen().apply { func() }

    @After
    fun tearDown() {
        unloadKoinModules(
            arrayListOf(
                testModule,
                testErrorModule)
        )
    }

    @Test
    fun whenStartActivity_mustLoadInitialCharacters() {
        given {
            initializeSucessDependencies()
            initializeActivity()
        }
        then { validateTitle(HERO_NAME) }
    }

    @Test
    fun whenStartActivity_showDialogError() {
        given {
            initializeErrorDependencies()
            initializeActivity()
        }
        then { validateVisibilityDialogError() }
    }
}
