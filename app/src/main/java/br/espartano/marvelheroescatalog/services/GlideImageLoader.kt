package br.espartano.marvelheroescatalog.services

import android.content.Context
import android.widget.ImageView
import br.espartano.marvelheroescatalog.interfaces.ImageLoader
import com.bumptech.glide.Glide

class GlideImageLoader : ImageLoader {

    override fun load(imageView: ImageView, context: Context, url: String) {
        Glide.with(context)
            .load(url)
            .into(imageView)
    }
}
