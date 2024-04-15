package com.example.myapplication.pruebas

import android.util.Log
import com.AG_AP.electroshop.endpoints.models.login.Login
import com.AG_AP.electroshop.endpoints.models.login.LoginReturn
import com.example.myapplication.data.RetrofitService
import okhttp3.ResponseBody
import retrofit2.Response

suspend fun makeApiRequest() {
    val apiService = RetrofitClient.retrofit.create(RetrofitService::class.java)

    try {
        val data = Login("PEPITO_ES","Usuario1234*","manager")
        val response: LoginReturn = apiService.listPopuralMovie(data)
        Log.d("HTTP_REQUEST", "Response Code: ${response.SessionId}")
    } catch (e: Exception) {
        Log.e("HTTP_REQUESTAaron", "Error: ${e.message}", e)
    }
}