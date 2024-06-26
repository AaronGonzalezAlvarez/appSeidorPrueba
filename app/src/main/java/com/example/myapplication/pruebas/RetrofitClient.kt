package com.example.myapplication.pruebas

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object RetrofitClient {

    private const val BASE_URL = "https://pc089n7l.seidor.es:50000/" // Tu URL base

    val retrofit: Retrofit by lazy {

        val trustAllCerts: Array<TrustManager> = arrayOf(
            object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                    // No hacer nada, simplemente confiar
                }

                override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                    // No hacer nada, simplemente confiar
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf() // No devolver nada para aceptar todos los certificados
                }
            }
        )

        val sslContext = SSLContext.getInstance("SSL").apply {
            init(null, trustAllCerts, SecureRandom())
        }

        val okHttpClient = OkHttpClient.Builder()
            .sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
            .hostnameVerifier { _, _ -> true } // Permitir todos los nombres de host
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
}