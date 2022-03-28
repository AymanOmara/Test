package com.example.test.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.dto.LogInData
import com.example.test.dto.Token
import com.example.test.dto.UserCredentials
import com.example.test.model.LocalModel
import com.example.test.model.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch

class ViewModel(private val repository: Repository):ViewModel() {
    var message = MutableLiveData<String>()
    var live = MutableLiveData<LogInCases>()
    private  var logInData: LogInData? = null
    private lateinit var localModel:LocalModel
    fun intailizeContext(context: Context){
         localModel = LocalModel(context)
    }

    fun callAPI(logInData:UserCredentials){

        viewModelScope.launch {
            live.value = LogInCases.startLoading
            val resposne = repository.login(logInData).single()
            this@ViewModel.logInData = resposne.body

            Log.d("my status code","${resposne.data?.code()}")
            Log.d("my status code", resposne.exception?.localizedMessage ?: "")
            responseValidator(resposne.data?.code(),resposne.exception?.localizedMessage ?: "")
    }
    }
    fun responseValidator(statueCode: Int?,message:String){
        live.value = LogInCases.stopLoading
        if(statueCode == 200){
            live.value = LogInCases.success
            logInData?.let { Token(logInData!!.token, it.refreshToken) }
                ?.let { localModel.savePrefrences(it) }
        }else if (statueCode == 400){
            live.value = LogInCases.wrongCard
        }else if(statueCode == 500 || statueCode == 503){
            this.message.value = "internal server error"
        }else if (statueCode == 401){
            this.message.value = "wrong email or password"
        }else if (message.equals("timed out")){
            this.message.value = "try again later"
        }
    }
    enum class LogInCases{
        startLoading,stopLoading,success,message,wrongCard


    }
}