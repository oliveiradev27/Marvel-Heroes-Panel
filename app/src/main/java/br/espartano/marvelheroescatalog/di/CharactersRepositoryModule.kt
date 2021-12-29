package br.espartano.marvelheroescatalog.di

import br.espartano.marvelheroescatalog.repository.CharactersNetworkRepository
import br.espartano.marvelheroescatalog.repository.CharactersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class CharactersRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindCharactersRepository(impl: CharactersNetworkRepository): CharactersRepository
}