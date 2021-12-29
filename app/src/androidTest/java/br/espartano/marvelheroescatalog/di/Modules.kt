package br.espartano.marvelheroescatalog.di

import br.espartano.marvelheroescatalog.extensions.TestImageLoader
import br.espartano.marvelheroescatalog.interfaces.ImageLoader
import br.espartano.marvelheroescatalog.repository.CharactersErrorTestRepository
import br.espartano.marvelheroescatalog.repository.CharactersRepository
import br.espartano.marvelheroescatalog.repository.CharactersTestRepository
import br.espartano.marvelheroescatalog.schedulers.SchedulerProvider
import br.espartano.marvelheroescatalog.schedulers.TrampolineSchedulerProvider
import br.espartano.marvelheroescatalog.viewmodels.CharactersViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn


@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [SchedulerModule::class]
)
abstract class SchedulersTestModules {

    @Binds
    abstract fun bindScheduler(impl: TrampolineSchedulerProvider): SchedulerProvider
}

/**
@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [CharactersRepositoryModule::class]
)
abstract class CharactersRepositoryTestModules {

    @Binds
    abstract fun bindRepository(impl: CharactersTestRepository): CharactersRepository
}
*/
@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [CharactersRepositoryModule::class]
)

abstract class MediaTestModules {

    @Binds
    abstract fun bindImageLoader(impl: TestImageLoader): ImageLoader
}