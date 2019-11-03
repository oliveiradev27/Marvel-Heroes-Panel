package br.espartano.marvelheroescatalog.ui.activities

import androidx.test.ext.junit.runners.AndroidJUnit4
import br.espartano.marvelheroescatalog.ui.activities.MainActivityRobotsConstants.HERO_NAME

import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private fun given(func: MainActivityGiven.() -> Unit) = MainActivityGiven().apply { func() }
    private fun `when`(func: MainActivityWhen.() -> Unit) = MainActivityWhen().apply { func() }
    private fun then(func: MainActivityThen.() -> Unit) = MainActivityThen().apply { func() }


    @Test
    fun whenStartActivity_mustLoadInitialCharacters() {
        given { initializeActivity() }
        then { validateTitle(HERO_NAME) }
    }
}