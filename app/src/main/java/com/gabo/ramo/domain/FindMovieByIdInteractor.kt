package com.gabo.ramo.domain

import com.gabo.ramo.data.Movie
import com.vicpin.krealmextensions.queryFirst

class FindMovieByIdInteractor : InteractorCommand<Movie?,String>{
    override suspend fun execute(id: String): Movie? {
        return Movie().queryFirst { equalTo("id",id) }
    }

}