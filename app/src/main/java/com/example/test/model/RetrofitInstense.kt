package com.example.test.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstense {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://amanat-jeddah-staging.azurewebsites.net/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
    val api:API by lazy {
        retrofit.create(API::class.java)
    }
}