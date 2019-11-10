package br.espartano.marvelheroescatalog.ui.adapters

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.espartano.marvelheroescatalog.R
import br.espartano.marvelheroescatalog.data.api.Character
import br.espartano.marvelheroescatalog.data.api.Thumbnail
import br.espartano.marvelheroescatalog.extensions.load

class CharactersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val imgHero: ImageView
    private val txtName: TextView
    private val txtDescription: TextView

    init {
        itemView.apply {
            imgHero = findViewById(R.id.iv_hero)
            txtName = findViewById(R.id.tv_hero_name)
            txtDescription = findViewById(R.id.tv_hero_description)
        }
    }

    fun bind(char: Character) {
        txtName.text = char.name
        setDescription(char.description)
        imgHero.load(buildUrlImage(char.thumbnail))
    }

    private fun setDescription(description : String) {
        if (!TextUtils.isEmpty(description)) {
            txtDescription.apply {
                text = description
                visibility = View.VISIBLE
            }
        } else {
            txtDescription.visibility = View.GONE
        }
    }

    private fun buildUrlImage(thumb: Thumbnail) =
        "${thumb.path}.${thumb.extension}"

}

class CharactersAdapter(private val characters : MutableList<Character>)
    : RecyclerView.Adapter<CharactersViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_hero_info, parent, false)

        return CharactersViewHolder(view)
    }

    override fun getItemCount(): Int = characters.size

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        holder.bind(characters[position])
    }

    fun add(chars: List<Character>) {
        characters.addAll(chars)
        notifyDataSetChanged()
    }

}