package br.espartano.marvelheroescatalog.usecase

import br.espartano.marvelheroescatalog.data.api.Character
import br.espartano.marvelheroescatalog.repository.CharactersRepository
import dagger.hilt.android.scopes.ActivityScoped
import io.reactivex.Observable
import javax.inject.Inject

class CharactersUseCase @Inject constructor(private val repository: CharactersRepository) {

    private val qtdItensPerPage = 10

    fun getCharacteres(page: Int): Observable<List<Character>>? {
        return repository.getCharacters(page * qtdItensPerPage)
    }
}
