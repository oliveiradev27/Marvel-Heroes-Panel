package br.espartano.marvelheroescatalog.extensions

import android.content.Context
import android.widget.ImageView
import br.espartano.marvelheroescatalog.R
import br.espartano.marvelheroescatalog.interfaces.ImageLoader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TestImageLoader @Inject constructor() : ImageLoader {

    override fun load(imageView: ImageView, context: Context, url: String) {
        imageView.setImageResource(R.mipmap.capitao_caverna)
    }
}
