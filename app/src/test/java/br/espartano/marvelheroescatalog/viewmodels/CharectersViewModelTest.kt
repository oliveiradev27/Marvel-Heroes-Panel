package br.espartano.marvelheroescatalog.viewmodels


import br.espartano.marvelheroescatalog.repository.CharactersRepository
import br.espartano.marvelheroescatalog.schedulers.TestSchedulerProvider
import br.espartano.marvelheroescatalog.schedulers.TrampolineSchedulerProvider
import br.espartano.marvelheroescatalog.usecase.CharactersUseCase
import io.mockk.mockk
import io.reactivex.schedulers.TestScheduler
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CharectersViewModelTest {

    private val trampolineSchedulerProvider = TrampolineSchedulerProvider()
    private val scheduler = TestScheduler()
    private val testProvider = TestSchedulerProvider(scheduler)
    private val repository = mockk<CharactersRepository>()
    private val useCase = CharactersUseCase(repository)

    @Test
    fun getCharacters() {
        val viewModel = CharactersViewModel(useCase, testProvider)
    }

}