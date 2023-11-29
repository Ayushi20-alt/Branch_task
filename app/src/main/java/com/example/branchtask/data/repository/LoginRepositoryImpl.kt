package com.example.branchtask.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.branchtask.common.Resource
import com.example.branchtask.data.remote.UserApi
import com.example.branchtask.domain.models.LoginResponse
import com.example.branchtask.domain.models.Loginmodel
import retrofit2.Response
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val api : UserApi
) {

    private val _userLiveDataresponse = MutableLiveData<Resource<LoginResponse>>()
    val userResponseLivedata : LiveData<Resource<LoginResponse>>
        get() = _userLiveDataresponse

    suspend fun getUserLogin(loginmodel: Loginmodel) {
        val response = api.login(loginmodel)
        if(response.isSuccessful && response.body() != null){
            _userLiveDataresponse.postValue(Resource.Success(response.body()!!))
        }
        else if(response.errorBody() != null)
        {
            _userLiveDataresponse.postValue(Resource.Error("Something went wrong"))
        }
        else{
            _userLiveDataresponse.postValue(Resource.Error("Something went wrong"))
        }
    }

}