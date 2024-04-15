package com.example.myapplication.data

import android.content.Context
import com.example.myapplication.R
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import java.security.cert.CertificateFactory
import java.util.concurrent.TimeUnit

object RetrofitServiceFactory {
    fun makeRetrofitService(applicationContext: Context): RetrofitService{
        val certificateInputStream = applicationContext.resources.openRawResource(R.raw.certidicado)
        val certificateFactory = CertificateFactory.getInstance("X.509")
        val certificate: X509Certificate = certificateFactory.generateCertificate(certificateInputStream) as X509Certificate

        val trustManager: X509TrustManager = object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}

            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                // Verificar que el certificado del servidor coincide con el certificado cargado
                chain?.forEach { serverCert ->
                    if (serverCert.equals(certificate)) {
                        return
                    }
                }
                throw CertificateException("El certificado del servidor no coincide con el certificado cargado")
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf(certificate)
            }
        }

        val sslContext: SSLContext = SSLContext.getInstance("TLS")
        sslContext.init(null, arrayOf<TrustManager>(trustManager), SecureRandom())

        val client = OkHttpClient.Builder()
            .sslSocketFactory(sslContext.socketFactory, trustManager)
            .hostnameVerifier { _, _ -> true }
            .connectTimeout(0, TimeUnit.MILLISECONDS) // Tiempo de espera de conexión infinito
            .readTimeout(0, TimeUnit.MILLISECONDS) // Tiempo de espera de lectura infinito
            .writeTimeout(0, TimeUnit.MILLISECONDS) // Tiempo de espera de escritura infinito
            .build()


        return Retrofit.Builder()
            .client(client)
            .baseUrl("https://pc089n7l.seidor.es:50000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RetrofitService::class.java)
    }
}