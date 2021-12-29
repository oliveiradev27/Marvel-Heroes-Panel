package br.espartano.marvelheroescatalog.repository

import br.espartano.marvelheroescatalog.data.api.Character
import br.espartano.marvelheroescatalog.services.MarvelApi
import io.reactivex.Observable
import javax.inject.Inject

class CharactersNetworkRepository @Inject constructor(): CharactersRepository {

    override fun getCharacters(page: Int): Observable<List<Character>>? {
        return MarvelApi
            .getService()
            .getAllCharacters(page)?.let {
                    it.flatMap { response -> Observable.just(response.data.results) }
            }
    }
}
