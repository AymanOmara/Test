package com.example.test.model


import com.example.test.dto.LogInData
import com.example.test.dto.UserCredentials
import kotlinx.coroutines.flow.Flow

import retrofit2.Response
import retrofit2.http.Body

import retrofit2.http.POST

 interface API {

    @POST("auth/token")
    suspend fun login(@Body data: UserCredentials):Response<LogInData>
}