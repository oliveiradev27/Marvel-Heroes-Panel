package br.espartano.marvelheroescatalog.di

import br.espartano.marvelheroescatalog.repository.CharactersErrorTestRepository
import br.espartano.marvelheroescatalog.repository.CharactersRepository
import br.espartano.marvelheroescatalog.repository.CharactersTestRepository
import br.espartano.marvelheroescatalog.schedulers.SchedulerProvider
import br.espartano.marvelheroescatalog.schedulers.TrampolineSchedulerProvider
import br.espartano.marvelheroescatalog.usecase.CharactersUseCase
import br.espartano.marvelheroescatalog.viewmodels.CharactersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val testModule = module {
    single <CharactersRepository>{ CharactersTestRepository() }
    factory { CharactersUseCase(get()) }
    viewModel { CharactersViewModel(get(), TrampolineSchedulerProvider()) }
}

val testErrorModule = module {
    single<CharactersRepository> { CharactersErrorTestRepository() }
    single<SchedulerProvider> { TrampolineSchedulerProvider() }
    factory {CharactersUseCase(get()) }
    viewModel { CharactersViewModel(get(), get()) }
}
