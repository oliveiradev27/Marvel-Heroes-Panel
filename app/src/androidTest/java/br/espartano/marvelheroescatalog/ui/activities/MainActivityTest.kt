package br.espartano.marvelheroescatalog.ui.activities

import androidx.test.ext.junit.runners.AndroidJUnit4
import br.espartano.marvelheroescatalog.repository.CharactersErrorTestRepository
import br.espartano.marvelheroescatalog.repository.CharactersRepository
import br.espartano.marvelheroescatalog.repository.CharactersTestRepository
import br.espartano.marvelheroescatalog.ui.activities.MainActivityRobotsConstants.HERO_DESCRIPTION
import br.espartano.marvelheroescatalog.ui.activities.MainActivityRobotsConstants.HERO_NAME
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MainActivityTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @BindValue
    var charactersTestRepository: CharactersRepository =  CharactersTestRepository()

    private fun given(func: MainActivityGiven.() -> Unit) = MainActivityGiven().apply { func() }
    private fun then(func: MainActivityThen.() -> Unit) = MainActivityThen().apply { func() }

    @Test
    fun whenStartActivity_mustLoadInitialCharacters() {
        given {
            initializeActivity()
        }
        then {
            validateTitle(HERO_NAME)
            validateDescrition(HERO_DESCRIPTION)
        }
    }

    @Test
    fun whenStartActivity_showDialogError() {
        setupErrorDependencies()
        given {
            initializeActivity()
        }
        then { validateVisibilityDialogError() }
    }

    private fun setupErrorDependencies() {
        charactersTestRepository = CharactersErrorTestRepository()
    }
}
