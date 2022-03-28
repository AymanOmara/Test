package com.example.test.model

import android.content.Context
import android.content.SharedPreferences
import com.example.test.Constants
import com.example.test.dto.Token
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LocalModel(context: Context) {
    private val sharedPreference = context.getSharedPreferences(Constants.sharedName, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreference.edit()

    fun savePrefrences(token: Token){
        editor.putString(Constants.token,token.token)
        editor.putString(Constants.refreshToken,token.refreshToken)
        editor.commit()
    }
    fun getPrefrences(): Flow<Token> {
        return flow {
            emit(Token(sharedPreference.getString(Constants.token,"")!!,sharedPreference.getString(Constants.refreshToken,"")!!))
        }
    }
    fun removeAllShared(){
        editor.clear()
        editor.commit()
    }
}