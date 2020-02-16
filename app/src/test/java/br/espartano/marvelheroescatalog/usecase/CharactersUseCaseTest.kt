package br.espartano.marvelheroescatalog.usecase

import br.espartano.marvelheroescatalog.data.api.Character
import br.espartano.marvelheroescatalog.data.api.Thumbnail
import br.espartano.marvelheroescatalog.repository.CharactersRepository
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import junit.framework.TestCase.assertEquals
import org.junit.Test

class CharactersUseCaseTest {

    @Test
    fun getCharacters() {
        val repository = mockk<CharactersRepository>()
        val useCase = CharactersUseCase(repository)

        every { repository.getCharacters(10) } returns Observable.just(mutableListOf(
            Character(1, "Espartano", "Overpower", Thumbnail("", ""))
        ))

        var nameCharacter : String? = null

        useCase.getCharacteres(1)?.subscribe {
                nameCharacter = it[0].name
        }

        assertEquals("Espartano", nameCharacter)
    }

    @Test
    fun getCharacters_returningError() {
        val repository = mockk<CharactersRepository>()
        val useCase = CharactersUseCase(repository)

        // given
        every { repository.getCharacters(any()) } returns Observable.error(Throwable("error"))

        // then
        var throwableMessage : String? = null
        useCase.getCharacteres(1)?.subscribe (
            { },
            { e -> throwableMessage = e.message },
            { }
        )
        assertEquals("error", throwableMessage)
    }
}