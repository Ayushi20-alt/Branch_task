package com.example.branchtask.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.branchtask.common.Resource
import com.example.branchtask.data.remote.HomeAPi
import com.example.branchtask.domain.models.Homemodel
import com.example.branchtask.domain.models.HomemodelItem
import com.example.branchtask.domain.models.MessageRequest
import okhttp3.Response
import okhttp3.ResponseBody
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val api : HomeAPi
)
{
    private val _homeData = MutableLiveData<Resource<MutableList<HomemodelItem>>>()
    val homeData get()  = _homeData

   var _notesLiveData = MutableLiveData<Resource<MutableList<HomemodelItem>>>()

    private val _postData = MutableLiveData<Resource<HomemodelItem>>()
    val postData get()= _postData

    private val _resetData = MutableLiveData<Resource<ResponseBody>>()
    val resetData: LiveData<Resource<ResponseBody>> = _resetData



    suspend fun getHomedata() {
        _notesLiveData.postValue(Resource.Loading())
        val response = api.getinfo()
        if (response.isSuccessful && response.body() != null) {
            _notesLiveData.postValue(Resource.Success(response.body()!!))
            _homeData.postValue(Resource.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            _notesLiveData.postValue(Resource.Error("Something Went wrong"))
        } else {
            _notesLiveData.postValue(Resource.Error("Something Went Wrong"))
        }
    }

    suspend fun makeNew(messageRequest: MessageRequest){
        _postData.postValue(Resource.Loading())
        val response = api.createMessage(messageRequest)
        if (response.isSuccessful && response.body() != null) {
            _postData.postValue(Resource.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            _postData.postValue(Resource.Error("Something Went wrong"))
        } else {
            _postData.postValue(Resource.Error("Something Went Wrong"))
        }

    }


    suspend fun resetData(){
        _resetData.postValue(Resource.Loading())
        val response = api.reset()
        if (response.isSuccessful && response.body() != null) {
            _notesLiveData = _homeData
            _resetData.postValue(Resource.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            _resetData.postValue(Resource.Error("Something Went wrong"))
        } else {
            _resetData.postValue(Resource.Error("Something Went Wrong"))
        }
    }


}