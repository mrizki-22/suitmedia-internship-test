package com.example.suitmediainternshiptest.data

import kotlinx.coroutines.flow.Flow

class UserRepository(
    private val apiService: ApiService,
    private val pref: Preference
) {

    fun getUserPagingSource(): UserPagingSource {
        return UserPagingSource(apiService)
    }

    fun getUsername() : Flow<String> {
        return pref.getUsername()
    }

    suspend fun setUsername(username: String) {
        pref.setUsername(username)
    }


    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(apiService: ApiService, pref: Preference): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, pref).apply { instance = this }
            }
    }
}