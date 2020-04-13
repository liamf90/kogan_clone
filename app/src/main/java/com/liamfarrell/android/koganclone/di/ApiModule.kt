package com.liamfarrell.android.koganclone.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.liamfarrell.android.koganclone.api.KoganApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import javax.inject.Singleton


const val BASE_URL = "https://m8h2drp85m.execute-api.ap-southeast-2.amazonaws.com/v1/"
@Module
class ApiModule {

    @Singleton
    @Provides
    fun provideSnapBattleService(gson: Gson): KoganApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(KoganApiService::class.java)
    }


    @Provides
    fun provideGson(): Gson{
        val builder = GsonBuilder()
        // Register an adapter to manage the date types as long values
        builder.registerTypeAdapter(Calendar::class.java,
            JsonDeserializer { json, typeOfT, context -> Calendar.getInstance().apply{timeInMillis = (json.asLong * 1000) }})
        return builder.create()
    }
}