package br.espartano.marvelheroescatalog.ui.activities

import androidx.test.ext.junit.runners.AndroidJUnit4
import br.espartano.marvelheroescatalog.data.api.Character
import br.espartano.marvelheroescatalog.data.api.Thumbnail
import br.espartano.marvelheroescatalog.repository.CharactersErrorTestRepository
import br.espartano.marvelheroescatalog.repository.CharactersRepository
import br.espartano.marvelheroescatalog.repository.CharactersTestRepository
import br.espartano.marvelheroescatalog.ui.activities.MainActivityRobotsConstants.HERO_DESCRIPTION
import br.espartano.marvelheroescatalog.ui.activities.MainActivityRobotsConstants.HERO_NAME
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MainActivityTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @BindValue
    var charactersTestRepository: CharactersRepository =  mockk(relaxed = true)

    private fun given(func: MainActivityGiven.() -> Unit) = MainActivityGiven().apply { func() }
    private fun then(func: MainActivityThen.() -> Unit) = MainActivityThen().apply { func() }

    @Test
    fun whenStartActivity_mustLoadInitialCharacters() {
        every { charactersTestRepository.getCharacters(any()) } returns Observable.just(
            listOf(
                Character(1, "Espartano", "Overpower",
                Thumbnail("", "")
                )
            )
        )

        given {
            initializeActivity()
        }
        then {
            validateTitle(HERO_NAME)
            validateDescription(HERO_DESCRIPTION)
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
