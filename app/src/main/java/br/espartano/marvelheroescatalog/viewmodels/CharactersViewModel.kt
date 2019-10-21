package br.espartano.marvelheroescatalog.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.espartano.marvelheroescatalog.data.api.Character
import br.espartano.marvelheroescatalog.services.MarvelApi
import br.espartano.marvelheroescatalog.viewmodels.states.CharactersStates
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CharactersViewModel : ViewModel() {

    private val characters = mutableListOf<Character>()
    private val statesLiveData = MutableLiveData<CharactersStates>()

    private var currentPage = -1

    init {
        statesLiveData.value = CharactersStates.InitialState
    }

    fun getStates() : LiveData<CharactersStates> = statesLiveData

    fun loadMoreCharacters() {
        val page = currentPage + 1
        load(page)
    }

    fun load(page : Int = 0) {
        statesLiveData.value = CharactersStates.Loading

        if (page <= currentPage) {
            statesLiveData.postValue(CharactersStates.Loaded(characters))
        } else {
            getMoreCharacters(page)
        }
    }

    private fun getMoreCharacters(page: Int) {
        currentPage = page
        MarvelApi
            .getService()
            .getAllCharacters(page * 20)?.let {
                it.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapIterable { response -> response.data.results }
                .subscribe (
                    { character -> characters.add(character) },
                    { e -> e.printStackTrace()
                        statesLiveData.value = CharactersStates.Error(e.message!!) },
                    { statesLiveData.value = CharactersStates.Loaded(characters) })
            }

    }

}