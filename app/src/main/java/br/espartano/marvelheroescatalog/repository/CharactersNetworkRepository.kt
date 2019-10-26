package br.espartano.marvelheroescatalog.repository

import br.espartano.marvelheroescatalog.data.api.Character
import br.espartano.marvelheroescatalog.services.MarvelApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CharactersNetworkRepository : CharactersRepository {

    override fun getCharacters(page: Int): Observable<List<Character>>? {
        return MarvelApi
            .getService()
            .getAllCharacters(page * 20)?.let {
                it.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .flatMap { response -> Observable.just(response.data.results) }
            }
    }
}