package br.espartano.marvelheroescatalog.extensions

import android.content.Context
import android.widget.ImageView
import br.espartano.marvelheroescatalog.R
import br.espartano.marvelheroescatalog.interfaces.ImageLoader

class TestImageLoader : ImageLoader {

    override fun load(imageView: ImageView, context: Context, url: String) {
        imageView.setImageResource(R.mipmap.capitao_caverna)
    }
}