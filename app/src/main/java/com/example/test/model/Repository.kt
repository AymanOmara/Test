package com.example.test.model

import com.example.test.dto.GenericDataClass
import com.example.test.dto.LogInData
import com.example.test.dto.UserCredentials
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import retrofit2.Response


class Repository {
    suspend fun login(data: UserCredentials):Flow<GenericDataClass<LogInData>>{
        return flow{
            emit(safeApiCall {RetrofitInstense.api.login(data) })
        }
    }
    private inline fun <T> safeApiCall(apicall: () -> Response<T>):GenericDataClass<T>{
        return try {
            GenericDataClass.success(apicall.invoke())
        }catch (e:Exception){
            GenericDataClass.failure(e)

        }
    }
}