package br.espartano.marvelheroescatalog.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.espartano.marvelheroescatalog.data.api.Character
import br.espartano.marvelheroescatalog.schedulers.SchedulerProvider
import br.espartano.marvelheroescatalog.usecase.GetCharactersUseCase
import br.espartano.marvelheroescatalog.viewmodels.states.CharactersStates
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {

    private val characters = mutableListOf<Character>()
    private val statesLiveData = MutableLiveData<CharactersStates>()

    private val initialStateIndexPage = -1
    private var currentPage = initialStateIndexPage

    init {
        statesLiveData.value = CharactersStates.InitialState
    }

    fun getStates(): LiveData<CharactersStates> = statesLiveData

    fun loadMoreCharacters(lastVisibleItemPosition: Int = currentPage + 1) {
        when (statesLiveData.value) {
            is CharactersStates.InitialState -> load()
            else -> load(lastVisibleItemPosition)
        }
    }

    private fun load(page: Int = 0) {
        if (page < characters.size - 1)
            return

        statesLiveData.value = CharactersStates.Loading

        handlerPagination(page)
    }

    private fun handlerPagination(page: Int) {
        if (page <= currentPage) {
            statesLiveData.value = CharactersStates.Loaded(characters)
        } else {
            getMoreCharacters(page)
        }
    }

    private fun getMoreCharacters(page: Int) {
        currentPage = page
        getCharactersUseCase(page = currentPage)?.let {
                it.subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                    { chars -> characters.addAll(chars) },
                    { e -> statesLiveData.value = CharactersStates.Error(e.message!!) },
                    { configureCurrentState() }
                )
            }
    }

    private fun configureCurrentState() {
        if (characters.isNotEmpty())
            statesLiveData.value = CharactersStates.Loaded(characters)
        else
            statesLiveData.value = CharactersStates.EmptyState
    }

    fun resetState() {
        currentPage = initialStateIndexPage
        characters.clear()
        statesLiveData.value = CharactersStates.InitialState
    }
}
