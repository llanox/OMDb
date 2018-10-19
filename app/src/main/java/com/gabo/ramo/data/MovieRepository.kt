package com.gabo.ramo.data

import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "http://www.omdbapi.com/"
const val API_KEY = "5eec5adc"
const val CACHE_SIZE_BYTES: Long = 1024 * 1024 * 4



interface MovieRepository {
    @GET("/")
    fun listMovieByQuery(@Query("apikey") apiKey: String = API_KEY, @Query("s") query: String): Deferred<MovieMapper.Result>

}