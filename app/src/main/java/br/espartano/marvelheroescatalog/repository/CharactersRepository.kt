package br.espartano.marvelheroescatalog.repository

import br.espartano.marvelheroescatalog.data.api.Character
import io.reactivex.Observable

interface CharactersRepository {

    fun getCharacters(page: Int) : Observable<List<Character>>?

}