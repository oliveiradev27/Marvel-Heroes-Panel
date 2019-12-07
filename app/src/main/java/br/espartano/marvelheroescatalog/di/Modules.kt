package br.espartano.marvelheroescatalog.di

import br.espartano.marvelheroescatalog.interfaces.ImageLoader
import br.espartano.marvelheroescatalog.repository.CharactersNetworkRepository
import br.espartano.marvelheroescatalog.repository.CharactersRepository
import br.espartano.marvelheroescatalog.schedulers.AppSchedulerProvider
import br.espartano.marvelheroescatalog.schedulers.SchedulerProvider
import br.espartano.marvelheroescatalog.services.GlideImageLoader
import br.espartano.marvelheroescatalog.usecase.CharactersUseCase
import br.espartano.marvelheroescatalog.viewmodels.CharactersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {
    single<CharactersRepository>{ CharactersNetworkRepository() }
    single<SchedulerProvider>{ AppSchedulerProvider() }
    single<ImageLoader> { GlideImageLoader() }
    factory { CharactersUseCase(get()) }
    viewModel { CharactersViewModel(get(), get()) }
}