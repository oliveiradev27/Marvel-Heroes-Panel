package br.espartano.marvelheroescatalog.di

import br.espartano.marvelheroescatalog.repository.CharactersRepository
import br.espartano.marvelheroescatalog.usecase.GetCharactersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
class CharactersModules {

    @ViewModelScoped
    @Provides
    fun provideCharactersUseCase(repository: CharactersRepository): GetCharactersUseCase =
        GetCharactersUseCase(repository = repository)
}