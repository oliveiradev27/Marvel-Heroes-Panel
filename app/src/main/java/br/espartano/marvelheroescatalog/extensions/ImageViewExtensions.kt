package br.espartano.marvelheroescatalog.extensions

import android.widget.ImageView
import br.espartano.marvelheroescatalog.extensions.ImageViewInjector.loader
import br.espartano.marvelheroescatalog.interfaces.ImageLoader
import org.koin.core.KoinComponent
import org.koin.core.inject

object ImageViewInjector: KoinComponent {
    val loader : ImageLoader by inject()
}

fun ImageView.load(url : String) {
    loader.load(this, context, url)
}