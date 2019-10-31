package br.espartano.marvelheroescatalog.usecase

import br.espartano.marvelheroescatalog.data.api.Character
import br.espartano.marvelheroescatalog.repository.CharactersRepository
import io.reactivex.Observable

class CharactersUseCase (private val repository: CharactersRepository) {

    fun getCharacteres(page: Int) : Observable<List<Character>>? {
        return repository.getCharacters(page * 10)
    }
}