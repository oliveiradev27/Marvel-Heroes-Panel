package br.espartano.marvelheroescatalog.repository

import br.espartano.marvelheroescatalog.data.api.Character
import io.reactivex.Observable

class CharactersErrorTestRepository: CharactersRepository {

    override fun getCharacters(page: Int): Observable<List<Character>>? {
        return Observable.error(Throwable("error"))
    }
}