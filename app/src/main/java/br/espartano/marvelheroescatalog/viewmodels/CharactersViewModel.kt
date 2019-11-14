package br.espartano.marvelheroescatalog.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.espartano.marvelheroescatalog.data.api.Character
import br.espartano.marvelheroescatalog.usecase.CharactersUseCase
import br.espartano.marvelheroescatalog.viewmodels.states.CharactersStates
import androidx.lifecycle.ViewModel
import br.espartano.marvelheroescatalog.schedulers.SchedulerProvider


class CharactersViewModel(private val useCase: CharactersUseCase,
                          private val schedulerProvider: SchedulerProvider) : ViewModel() {

    private val characters = mutableListOf<Character>()
    private val statesLiveData = MutableLiveData<CharactersStates>()

    private var currentPage = -1

    init {
        statesLiveData.value = CharactersStates.InitialState
    }

    fun getStates() : LiveData<CharactersStates> = statesLiveData

    fun loadMoreCharacters(lastVisibleItemPosition : Int = currentPage + 1) {
        when (statesLiveData.value) {
            is CharactersStates.InitialState -> load(lastVisibleItemPosition)
            else -> {
                if (lastVisibleItemPosition >= characters.size -1) load(lastVisibleItemPosition)
            }

        }

    }

    private fun load(page : Int = 0) {
        statesLiveData.value = CharactersStates.Loading

        if (page <= currentPage) {
            statesLiveData.value = CharactersStates.Loaded(characters)
        } else {
            getMoreCharacters(page)
        }
    }

    private fun getMoreCharacters(page: Int) {
        currentPage = page
        useCase.getCharacteres(page =  currentPage)?.let {
                it.subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe (
                    { chars -> characters.addAll(chars) },
                    { e -> statesLiveData.value = CharactersStates.Error(e.message!!) },
                    {
                        if (characters.isNotEmpty())
                             statesLiveData.value = CharactersStates.Loaded(characters)
                        else
                            statesLiveData.value = CharactersStates.EmptyState
                    }
                )
            }
    }

    fun resetState() {
        currentPage = -1
        characters.clear()
        statesLiveData.value = CharactersStates.InitialState
    }
}