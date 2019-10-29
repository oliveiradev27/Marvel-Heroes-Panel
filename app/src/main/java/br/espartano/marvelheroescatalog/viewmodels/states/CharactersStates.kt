package br.espartano.marvelheroescatalog.viewmodels.states

import br.espartano.marvelheroescatalog.data.api.Character

sealed class CharactersStates {

    object InitialState : CharactersStates()
    object Loading : CharactersStates()
    class Loaded(val caracters : List<Character>) : CharactersStates()
    object EmptyState : CharactersStates()
    data class Error(val message : String) : CharactersStates()
}