package br.espartano.marvelheroescatalog.extensions

import android.widget.ImageView
import br.espartano.marvelheroescatalog.services.GlideImageLoader


fun ImageView.load(url: String) {
    val loader = GlideImageLoader()
    loader.load(this, context, url)
}
