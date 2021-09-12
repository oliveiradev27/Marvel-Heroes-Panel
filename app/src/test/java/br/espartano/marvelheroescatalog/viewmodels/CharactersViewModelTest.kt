package br.espartano.marvelheroescatalog.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.espartano.marvelheroescatalog.data.api.Character
import br.espartano.marvelheroescatalog.data.api.Thumbnail
import br.espartano.marvelheroescatalog.repository.CharactersRepository
import br.espartano.marvelheroescatalog.schedulers.TrampolineSchedulerProvider
import br.espartano.marvelheroescatalog.usecase.CharactersUseCase
import br.espartano.marvelheroescatalog.viewmodels.states.CharactersStates
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CharactersViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private val trampolineSchedulerProvider = TrampolineSchedulerProvider()
    private val repository = mockk<CharactersRepository>()
    private val useCase = CharactersUseCase(repository)

    @Test
    fun `when using resetState, must update state to Initial`() {
        // given
        val viewModel = CharactersViewModel(useCase, trampolineSchedulerProvider)

        // when
        viewModel.resetState()

        // then
        assertEquals(CharactersStates.InitialState, viewModel.getStates().value)
    }

    @Test
    fun `when using getCharacters, must update state to Error`() {
        // given
        val viewModel = CharactersViewModel(useCase, trampolineSchedulerProvider)
        every { repository.getCharacters(any()) } returns Observable.error(Throwable("error"))

        // when
        viewModel.loadMoreCharacters()

        // then
        assertEquals(CharactersStates.Error("error"), viewModel.getStates().value)
    }

    @Test
    fun `when using getCharacters when there is no return results, must update state for Empty`() {
        // given
        val viewModel = CharactersViewModel(useCase, trampolineSchedulerProvider)
        every { repository.getCharacters(any()) } returns Observable.just(arrayListOf())

        // when
        viewModel.loadMoreCharacters()

        // then
        assertEquals(CharactersStates.EmptyState, viewModel.getStates().value)
    }

    @Test
    fun `when using getCharacter, must update state to Loaded`() {
        // given
        val viewModel = CharactersViewModel(useCase, trampolineSchedulerProvider)
        val characters = arrayListOf(
            Character(
                1,
                "Espartano",
                "Overpower",
                Thumbnail("", "")
            )
        )
        every { repository.getCharacters(any()) } returns Observable.just(characters)

        // when
        viewModel.loadMoreCharacters()

        // then
        assert(viewModel.getStates().value is CharactersStates.Loaded)
    }

    @Test
    fun `when using getCharacters many twices, must update to Loaded and return all characters`() {
        // given
        val viewModel = CharactersViewModel(useCase, trampolineSchedulerProvider)

        every { repository.getCharacters(0) } returns Observable.just(
            arrayListOf(
                Character(
                    1,
                    "Espartano",
                    "Overpower",
                    Thumbnail("", "")
                )
            )
        )
        every { repository.getCharacters(10) } returns Observable.just(
            arrayListOf(
                Character(
                    1,
                    "Asgardiano",
                    "Mid Power",
                    Thumbnail("", "")
                )
            )
        )

        // when
        viewModel.loadMoreCharacters()
        viewModel.loadMoreCharacters()

        // then
        assert(viewModel.getStates().value is CharactersStates.Loaded)

        val state = viewModel.getStates().value as CharactersStates.Loaded
        assertEquals(state.characters[0].name, "Espartano")
        assertEquals(state.characters[1].name, "Asgardiano")
    }

    @Test
    fun `when using getMoreCharacters, must update state to Loaded and return all characters`() {
        // given
        val viewModel = CharactersViewModel(useCase, trampolineSchedulerProvider)

        every { repository.getCharacters(0) } returns Observable.just(
            arrayListOf(
                Character(
                    1,
                    "Espartano",
                    "Overpower",
                    Thumbnail("", "")
                )
            )
        )

        // when
        viewModel.loadMoreCharacters()

        // then
        assert(viewModel.getStates().value is CharactersStates.Loaded)

        val state = viewModel.getStates().value as CharactersStates.Loaded
        assertTrue(state.characters.size == 1)
        assertEquals(state.characters[0].name, "Espartano")
    }

    @Test
    fun `when using getMoreCharacters without first loading, should return only the first page`() {
        // given
        val viewModel = CharactersViewModel(useCase, trampolineSchedulerProvider)

        every { repository.getCharacters(0) } returns Observable.just(
            arrayListOf(
                Character(
                    1,
                    "Espartano",
                    "Overpower",
                    Thumbnail("", "")
                )
            )
        )

        // when
        viewModel.loadMoreCharacters()
        viewModel.loadMoreCharacters(0)

        // then
        val state = viewModel.getStates().value as CharactersStates.Loaded
        assert(viewModel.getStates().value is CharactersStates.Loaded)
        assertTrue(state.characters.size == 1)
        assertEquals(state.characters[0].name, "Espartano")
    }
}
