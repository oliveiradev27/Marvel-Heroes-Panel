package br.espartano.marvelheroescatalog.usecase

import br.espartano.marvelheroescatalog.data.api.Character
import br.espartano.marvelheroescatalog.data.api.Thumbnail
import br.espartano.marvelheroescatalog.repository.CharactersRepository
import br.espartano.marvelheroescatalog.schedulers.TestSchedulerProvider
import br.espartano.marvelheroescatalog.schedulers.TrampolineSchedulerProvider
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import junit.framework.Assert.assertEquals
import org.junit.Test

class CharactersUseCaseTest {
    private val trampolineSchedulerProvider = TrampolineSchedulerProvider()
    private val scheduler = TestScheduler()
    private val testProvider = TestSchedulerProvider(scheduler)

    @Test
    fun getCharacters() {
        val repository = mockk<CharactersRepository>()
        val useCase = CharactersUseCase(repository)

        every { repository.getCharacters(1) } returns Observable.just(mutableListOf(
            Character(1, "Espartano", "Overpower", Thumbnail("", ""))
        ))

        var nameCharacter : String? = null

        useCase.getCharacteres(1)?.subscribe {
                nameCharacter = it[0].name
        }

        assertEquals("Espartano", nameCharacter)
    }
}