package br.espartano.marvelheroescatalog.usecase

import br.espartano.marvelheroescatalog.data.api.Character
import br.espartano.marvelheroescatalog.repository.CharactersRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(private val repository: CharactersRepository) {

    private val quantityItemsPerPage = 10

    operator fun invoke(page: Int): Observable<List<Character>>? {
        return repository.getCharacters(page * quantityItemsPerPage)
    }
}
