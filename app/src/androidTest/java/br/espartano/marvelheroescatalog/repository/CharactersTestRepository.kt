package br.espartano.marvelheroescatalog.repository

import br.espartano.marvelheroescatalog.data.api.Character
import br.espartano.marvelheroescatalog.data.api.Thumbnail
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharactersTestRepository @Inject constructor() : CharactersRepository {

    override fun getCharacters(page: Int): Observable<List<Character>>? {
        return Observable.just(
            listOf(Character(1, "Espartano", "Overpower",
                Thumbnail("", ""))
            )
        )
    }
}
