package com.bardxhong.crypto.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CurrencyInfoRemoteEntity(
    val id: String,
    val name: String,
    val symbol: String,
)
