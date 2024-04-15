package com.example.myapplication.data

import com.AG_AP.electroshop.endpoints.models.login.Login
import com.AG_AP.electroshop.endpoints.models.login.LoginReturn
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RetrofitService {
    @POST("b1s/v1/Login")
    suspend fun listPopuralMovie(@Body data: Login): LoginReturn
}