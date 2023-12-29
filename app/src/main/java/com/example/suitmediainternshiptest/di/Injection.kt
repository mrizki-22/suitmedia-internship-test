package com.example.suitmediainternshiptest.di

import android.content.Context
import com.example.suitmediainternshiptest.data.ApiService
import com.example.suitmediainternshiptest.data.Preference
import com.example.suitmediainternshiptest.data.UserRepository
import com.example.suitmediainternshiptest.data.datastore
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Injection {
    fun provideUserRepository(context: Context): UserRepository {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://reqres.in/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        val pref = Preference.getInstance(context.datastore)
        return UserRepository.getInstance(apiService, pref)
    }
}