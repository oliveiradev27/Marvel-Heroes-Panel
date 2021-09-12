package br.espartano.marvelheroescatalog.paging

import br.espartano.marvelheroescatalog.services.MarvelApi
import io.reactivex.disposables.CompositeDisposable

class CharactersDataSource(
    private val marvelApi: MarvelApi,
    private val compositeDisposable: CompositeDisposable
)
