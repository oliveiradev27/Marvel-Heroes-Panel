package br.espartano.marvelheroescatalog.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.espartano.marvelheroescatalog.data.api.Character
import br.espartano.marvelheroescatalog.repository.CharactersNetworkRepository
import br.espartano.marvelheroescatalog.usecase.CharactersUseCase
import br.espartano.marvelheroescatalog.viewmodels.states.CharactersStates
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CharactersViewModel : ViewModel() {

    private val useCase = CharactersUseCase(CharactersNetworkRepository())
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
        useCase.getCharacteres(page =  currentPage)?.let {
                it.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ chars -> characters.addAll(chars) },
                    { e -> e.printStackTrace()
                        statesLiveData.value = CharactersStates.Error(e.message!!) },
                    { statesLiveData.value = CharactersStates.Loaded(characters) })
            }

    }

}