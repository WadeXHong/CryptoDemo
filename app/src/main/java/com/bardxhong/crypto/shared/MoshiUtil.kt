package com.bardxhong.crypto.shared

import com.squareup.moshi.Moshi

object MoshiUtil {
    val moshi: Moshi by lazy {
        Moshi.Builder().build()
    }
}