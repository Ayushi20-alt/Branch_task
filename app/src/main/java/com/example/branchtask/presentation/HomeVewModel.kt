package com.example.branchtask.presentation

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.branchtask.common.Resource
import com.example.branchtask.data.repository.HomeRepository
import com.example.branchtask.domain.models.HomemodelItem
import com.example.branchtask.domain.models.MessageRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import javax.inject.Inject

@HiltViewModel
class HomeVewModel @Inject constructor(
    private val repo : HomeRepository
) : ViewModel(){

    private var _notesLiveData = repo._notesLiveData
    val notesLiveData get() = _notesLiveData

//    val notesLiveData get() = repo.notesLiveData
    val postLiveData get() = repo.postData
    val resetData get() = repo.resetData
    val homeData get() = repo.homeData

    fun getAllNotes() {
        viewModelScope.launch {
           repo.getHomedata()
        }
    }

    fun makeNew(messageRequest: MessageRequest) {
        viewModelScope.launch {
            repo.makeNew(messageRequest)
        }
    }

    fun resetdata(){
        viewModelScope.launch {
            repo.resetData()
//            when(resetData){
//                is Resource.Success ->{
//                    homeviewmodel.notesLiveData.value = homeviewmodel.homeData.value
//                    Toast.makeText(requireContext(),"ResetSucessfully", Toast.LENGTH_LONG).show()
//                }
//                is Resource.Error ->{
//                    Toast.makeText(requireContext(),"Reset not Sucessfully", Toast.LENGTH_LONG).show()
//                }
//                is Resource.Loading->{
//
//                }
//            }
        }
    }

    fun getSortedData(data: List<HomemodelItem>): Map<String, List<HomemodelItem>> {
        var groupedData = data.groupBy { it.thread_id}
        val sortedMessages = groupedData.mapValues { (_, messages) ->
            messages.sortedByDescending { it.timestamp }
        }
        return sortedMessages
    }


}