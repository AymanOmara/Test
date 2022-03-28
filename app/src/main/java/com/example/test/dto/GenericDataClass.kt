package com.example.test.dto

import retrofit2.Response
import java.lang.Exception

data class GenericDataClass<T>(
    val status:Status?,
    val data: Response<T>?,
    val exception: Exception?
){
    companion object{
        fun <T> success(data: Response<T>):GenericDataClass<T>{
            return  GenericDataClass(status = Status.Success,data = data,exception = null)
        }
        fun <T> failure(exception: Exception?):GenericDataClass<T>{

            return GenericDataClass(status = Status.Failure,data = null,  exception = exception)
        }
    }
    sealed class Status{
        object Success:Status()
        object Failure:Status()
    }

val faild:Boolean
    get() = this.status ==  Status.Failure


    val isSuccess:Boolean
    get() = !faild && this.data?.isSuccessful == true

    val body:T
    get() = this.data?.body()!!

}
