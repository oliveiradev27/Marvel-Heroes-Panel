package br.espartano.marvelheroescatalog.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProviders
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.espartano.marvelheroescatalog.data.api.Character
import br.espartano.marvelheroescatalog.data.api.Thumbnail
import br.espartano.marvelheroescatalog.repository.CharactersRepository
import br.espartano.marvelheroescatalog.schedulers.TrampolineSchedulerProvider
import br.espartano.marvelheroescatalog.ui.activities.MainActivity
import br.espartano.marvelheroescatalog.usecase.CharactersUseCase
import br.espartano.marvelheroescatalog.viewmodels.states.CharactersStates
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CharactersViewModelAndroidXTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private val trampolineSchedulerProvider = TrampolineSchedulerProvider()
    private val repository = mockk<CharactersRepository>()
    private val useCase = CharactersUseCase(repository)

    @Test
    fun `when creating object by Factory, it must work correctly`() {
        //given
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.moveToState(Lifecycle.State.CREATED)

        scenario.onActivity {
            val viewModel = ViewModelProviders
                .of(it, CharactersViewModel.get(useCase, trampolineSchedulerProvider))
                .get(CharactersViewModel::class.java)

            every { repository.getCharacters(0) } returns Observable.just(
                arrayListOf(
                    Character(1, "Espartano", "Overpower", Thumbnail("", ""))
                )
            )

            //when
            viewModel.load()

            //then
            assert(viewModel.getStates().value is CharactersStates.Loaded)
            //val state = viewModel.getStates().value as CharactersStates.Loaded
            //TestCase.assertTrue(state.caracters.size == 1)
        }
    }
}