package br.espartano.marvelheroescatalog.di

import br.espartano.marvelheroescatalog.repository.CharactersNetworkRepository
import br.espartano.marvelheroescatalog.repository.CharactersRepository
import br.espartano.marvelheroescatalog.schedulers.AppSchedulerProvider
import br.espartano.marvelheroescatalog.usecase.CharactersUseCase
import br.espartano.marvelheroescatalog.viewmodels.CharactersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {
    single<CharactersRepository>{ CharactersNetworkRepository() }
    single{ AppSchedulerProvider() }
    factory { CharactersUseCase(get()) }
    viewModel { CharactersViewModel(get(), get()) }
}