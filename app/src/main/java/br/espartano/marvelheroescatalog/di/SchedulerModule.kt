package br.espartano.marvelheroescatalog.di

import br.espartano.marvelheroescatalog.schedulers.AppSchedulerProvider
import br.espartano.marvelheroescatalog.schedulers.SchedulerProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class SchedulerModule {

    @Singleton
    @Binds
    abstract fun bindSchedulers(appSchedulerProvider: AppSchedulerProvider): SchedulerProvider
}