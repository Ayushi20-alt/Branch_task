package com.example.branchtask.presentation

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.branchtask.common.Resource
import com.example.branchtask.data.repository.LoginRepositoryImpl
import com.example.branchtask.domain.models.LoginResponse
import com.example.branchtask.domain.models.Loginmodel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginRepositoryImpl: LoginRepositoryImpl
)
: ViewModel() {

    val userRegisterLiveData : LiveData<Resource<LoginResponse>>
        get() =  loginRepositoryImpl.userResponseLivedata

     fun loginuser(loginmodel: Loginmodel){
         viewModelScope.launch {
             loginRepositoryImpl.getUserLogin(loginmodel)
         }
     }

    fun validatecredentials(emailaddress : String, password : String) : Pair<Boolean, String>{
        var result = Pair(true, "")
        val passwordexpected = emailaddress.reversed()
        if(TextUtils.isEmpty(emailaddress) || TextUtils.isEmpty(password) || password != passwordexpected)
        {
            result= Pair(false, "Please provide the credentials")
        }else if(!Patterns.EMAIL_ADDRESS.matcher(emailaddress).matches())
        {
           result = Pair(false, "Please provide valid email")
        }
        return result
    }
}