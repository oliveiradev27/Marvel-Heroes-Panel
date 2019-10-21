package br.espartano.marvelheroescatalog.data.api

data class Character(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: Thumbnail
)