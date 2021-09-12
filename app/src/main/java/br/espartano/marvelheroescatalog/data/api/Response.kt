package br.espartano.marvelheroescatalog.data.api

data class Response(
    val code: Int,
    val etag: String,
    val data: Data
)
