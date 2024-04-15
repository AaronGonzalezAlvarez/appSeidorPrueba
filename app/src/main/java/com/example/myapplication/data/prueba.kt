package com.example.myapplication.data

import okhttp3.OkHttpClient
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

fun main() {
    // Crear un trust manager que no valide los certificados SSL
    val trustAllCerts: Array<TrustManager> = arrayOf(
        object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }

            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        }
    )

    // Configurar el trust manager para aceptar todos los certificados
    val sslContext = SSLContext.getInstance("TLS")
    sslContext.init(null, trustAllCerts, java.security.SecureRandom())

    val trustManager: X509TrustManager = trustAllCerts[0] as X509TrustManager

    val client = OkHttpClient.Builder()
        .sslSocketFactory(sslContext.socketFactory, trustManager)
        .hostnameVerifier { _, _ -> true }
        .build()
}