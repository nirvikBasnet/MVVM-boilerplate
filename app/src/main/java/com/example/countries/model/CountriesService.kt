package com.example.countries.model

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CountriesService  {

    private val BASE_URL = "https://raw.githubusercontent.com"
    private val api: CountriesApi

    init {
        api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) //JSON to Model (Country)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // transforms data into observable to subscribe to
            .build()
            .create(CountriesApi::class.java)
    }

    fun getCountries() : Single<List<Country>>{
        return api.getCountries()
    }
}