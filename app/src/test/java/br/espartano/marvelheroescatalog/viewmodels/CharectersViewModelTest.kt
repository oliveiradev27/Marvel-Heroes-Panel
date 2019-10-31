package br.espartano.marvelheroescatalog.viewmodels


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.espartano.marvelheroescatalog.repository.CharactersRepository
import br.espartano.marvelheroescatalog.schedulers.TrampolineSchedulerProvider
import br.espartano.marvelheroescatalog.usecase.CharactersUseCase
import br.espartano.marvelheroescatalog.viewmodels.states.CharactersStates
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import junit.framework.TestCase.assertEquals
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
    fun getCharacters_validStateError() {
        //given
        val viewModel = CharactersViewModel(useCase, trampolineSchedulerProvider)
        every { repository.getCharacters(any()) } returns Observable.error(Throwable("error"))

        //when
        viewModel.load()

        //then
        assertEquals(CharactersStates.Error("error"), viewModel.getStates().value)
    }

    @Test
    fun getCharacters_validStateEmpty() {
        //given
        val viewModel = CharactersViewModel(useCase, trampolineSchedulerProvider)
        every { repository.getCharacters(any()) } returns Observable.just(arrayListOf())

        //when
        viewModel.load()

        //then
        assertEquals(CharactersStates.EmptyState, viewModel.getStates().value)
    }
}