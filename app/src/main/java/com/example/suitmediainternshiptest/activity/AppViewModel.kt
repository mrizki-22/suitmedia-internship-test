package com.example.suitmediainternshiptest.activity

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.suitmediainternshiptest.data.User
import com.example.suitmediainternshiptest.data.UserRepository
import com.example.suitmediainternshiptest.di.Injection
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AppViewModel(private val userRepository: UserRepository) : ViewModel() {
    val users: LiveData<PagingData<User>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = {
            userRepository.getUserPagingSource()
        }
    ).liveData

    private val _selectedUsername = MutableLiveData("")
    val selectedUsername = _selectedUsername

    fun setSelectedUsername(username: String) {
        viewModelScope.launch {
            userRepository.setUsername(username)
        }
    }

    fun getSelectedUsername() {
        viewModelScope.launch {
         userRepository.getUsername().collectLatest {
             _selectedUsername.value = it
         }
        }
    }
}


class AppViewModelFactory private constructor(private val userRepository: UserRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AppViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    companion object {
        @Volatile
        private var instance: AppViewModelFactory? = null
        fun getInstance(context: Context): AppViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: AppViewModelFactory(Injection.provideUserRepository(context)).also {
                    instance = it
                }
            }
    }
}