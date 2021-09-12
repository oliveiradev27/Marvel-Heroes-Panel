package br.espartano.marvelheroescatalog.repository

import br.espartano.marvelheroescatalog.data.api.Character
import br.espartano.marvelheroescatalog.data.api.Thumbnail
import io.reactivex.Observable

class CharactersTestRepository : CharactersRepository {

    override fun getCharacters(page: Int): Observable<List<Character>>? {
        return Observable.just(
            listOf(Character(1, "Espartano", "Overpower",
                Thumbnail("", ""))
            )
        )
    }
}
