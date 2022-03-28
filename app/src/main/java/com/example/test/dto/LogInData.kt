package com.example.test.dto

data class LogInData(var token:String,var status:String,var refreshToken:String)
data class UserCredentials(var email:String,var password:String)