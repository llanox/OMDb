package com.gabo.ramo.data

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey



open class Movie(@PrimaryKey @SerializedName("imdbID") var id: String = "",
                 @SerializedName("Title") var title: String = "",
                 @SerializedName("Poster") var posterPath: String? = null,
                 var overview: String? = null): RealmObject()

sealed class Response<out T : Any> {

    class Success<out T : Any>(val data: T) : Response<T>()

    class Error(val error: Throwable = Throwable(), val errorMsg: String = "") : Response<Nothing>()
}

object MovieMapper {
    data class Result(@SerializedName("Search") val results: List<Movie>)
}