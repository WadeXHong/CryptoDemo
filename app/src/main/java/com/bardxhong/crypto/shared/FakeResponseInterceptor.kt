package com.bardxhong.crypto.shared

import com.bardxhong.crypto.CryptoApplication
import com.bardxhong.crypto.R
import com.bardxhong.crypto.data.remote.NetworkInfo
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.IOException

class FakeResponseInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val response = when (originalRequest.url.pathSegments.joinToString("/")) {
            NetworkInfo.PATH_CURRENCY_INFO ->
                Response.Builder()
                    .code(200)
                    .body(
                        fakeCurrencyListInfo().toResponseBody()
                    )
                    .request(originalRequest)
                    .protocol(Protocol.HTTP_2)
                    .message("fake response message")
                    .build()
            else -> throw IOException("This api do not support any fake response")
        }
        return response
    }

    private fun fakeCurrencyListInfo(): String =
        CryptoApplication.appContext.resources
            .openRawResource(R.raw.currency_list_info)
            .bufferedReader()
            .use { it.readText() }

}