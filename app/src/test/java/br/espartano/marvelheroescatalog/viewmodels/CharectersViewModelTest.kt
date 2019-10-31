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
class CharectersViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule: InstantTaskExecutorRule  = InstantTaskExecutorRule()

    private val trampolineSchedulerProvider = TrampolineSchedulerProvider()
    private val repository = mockk<CharactersRepository>()
    private val useCase = CharactersUseCase(repository)

    @Test
    fun resetState_shouldUpdateStateForInitialState() {
        //given
        val viewModel = CharactersViewModel(useCase, trampolineSchedulerProvider)

        //when
        viewModel.resetState()

        //then
        assertEquals(CharactersStates.InitialState, viewModel.getStates().value)
    }

    @Test
    fun getCharacters_shouldUpdateStateForError() {
        //given
        val viewModel = CharactersViewModel(useCase, trampolineSchedulerProvider)
        every { repository.getCharacters(any()) } returns Observable.error(Throwable("error"))

        //when
        viewModel.load()

        //then
        assertEquals(CharactersStates.Error("error"), viewModel.getStates().value)
    }

    @Test
    fun getCharacters_shouldUpdateStateForEmpty() {
        //given
        val viewModel = CharactersViewModel(useCase, trampolineSchedulerProvider)
        every { repository.getCharacters(any()) } returns Observable.just(arrayListOf())

        //when
        viewModel.load()

        //then
        assertEquals(CharactersStates.EmptyState, viewModel.getStates().value)
    }

    @Test
    fun getCharacters_shouldUpdateStateForLoaded() {
        //given
        val viewModel = CharactersViewModel(useCase, trampolineSchedulerProvider)
        val characters = arrayListOf(
            Character(1, "Espartano", "Overpower", Thumbnail("", ""))
        )
        every { repository.getCharacters(any()) } returns Observable.just(characters)

        //when
        viewModel.load()

        //then
        assert(viewModel.getStates().value is CharactersStates.Loaded)
    }

    @Test
    fun getCharactersManyTwices_shouldUpdateStateForLoadedAllCharacters() {
        //given
        val viewModel = CharactersViewModel(useCase, trampolineSchedulerProvider)

        every { repository.getCharacters(0) } returns Observable.just(arrayListOf(
                Character(1, "Espartano", "Overpower", Thumbnail("", ""))
            )
        )
        every { repository.getCharacters(10) } returns Observable.just(
            arrayListOf(
                Character(1, "Asgardiano", "Mid Power", Thumbnail("", ""))
            )
        )

        //when
        viewModel.load()
        viewModel.loadMoreCharacters()


        //then
        assert(viewModel.getStates().value is CharactersStates.Loaded)

        val state = viewModel.getStates().value as CharactersStates.Loaded
        assertEquals(state.caracters[0].name, "Espartano")
        assertEquals(state.caracters[1].name, "Asgardiano")
    }

    @Test
    fun getMoreCharacters_shouldUpdateStateForLoadedAllCharacters() {
        //given
        val viewModel = CharactersViewModel(useCase, trampolineSchedulerProvider)

        every { repository.getCharacters(0) } returns Observable.just(
            arrayListOf(
              Character(1, "Espartano", "Overpower", Thumbnail("", ""))
            )
        )

        //when
        viewModel.loadMoreCharacters()


        //then
        assert(viewModel.getStates().value is CharactersStates.Loaded)

        val state = viewModel.getStates().value as CharactersStates.Loaded
        assertTrue(state.caracters.size == 1)
        assertEquals(state.caracters[0].name, "Espartano")
    }

    @Test
    fun getMoreCharactersTwoTwicesWithNotPaginate_shouldUpdateStateForLoadedOneCharacter() {
        //given
        val viewModel = CharactersViewModel(useCase, trampolineSchedulerProvider)

        every { repository.getCharacters(0) } returns Observable.just(
            arrayListOf(
                Character(1, "Espartano", "Overpower", Thumbnail("", ""))
            )
        )

        //when
        viewModel.load()
        viewModel.load()


        //then
        assert(viewModel.getStates().value is CharactersStates.Loaded)

        val state = viewModel.getStates().value as CharactersStates.Loaded
        assertTrue(state.caracters.size == 1)
        assertEquals(state.caracters[0].name, "Espartano")
    }
}