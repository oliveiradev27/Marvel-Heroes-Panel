package br.espartano.marvelheroescatalog.di

import br.espartano.marvelheroescatalog.interfaces.ImageLoader
import br.espartano.marvelheroescatalog.services.GlideImageLoader
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class MediaModule {

    @Singleton
    @Binds
    abstract fun bindImageLoader(imageLoader: GlideImageLoader): ImageLoader
}