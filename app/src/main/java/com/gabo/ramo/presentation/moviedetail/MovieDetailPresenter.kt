package com.gabo.ramo.presentation.moviedetail

import com.gabo.ramo.core.BasePresenter
import com.gabo.ramo.core.CoroutineContextProvider
import com.gabo.ramo.domain.FindMovieByIdInteractor
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.launch

class MovieDetailPresenter : BasePresenter<MovieDetailScreen>() {
    private val contextPool: CoroutineContextProvider = CoroutineContextProvider()

    fun init(movieId: String) {
        showMovie(movieId)
    }

    private fun showMovie(movieId: String) {
        GlobalScope.launch(contextPool.Main) {
            val movie = FindMovieByIdInteractor().execute(movieId)

            view?.renderMovieDetail(movie)

        }

    }


}