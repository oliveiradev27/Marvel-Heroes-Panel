package br.espartano.marvelheroescatalog.interfaces

import android.content.Context
import android.widget.ImageView

interface ImageLoader {
    fun load(imageView: ImageView, context: Context, url: String)
}
