package com.bardxhong.crypto

import android.app.Application

class CryptoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        _appContext = this
    }

    companion object {
        private var _appContext: CryptoApplication? = null
        val appContext: CryptoApplication get() = _appContext!!
    }
}