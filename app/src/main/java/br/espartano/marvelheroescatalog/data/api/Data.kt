package br.espartano.marvelheroescatalog.data.api

data class Data(
    val offset : Int,
    val limit : Int,
    val total : Int,
    val count : Int,
    val results : List<Character>
)