package com.gabo.ramo.presentation.search

import android.content.Context
import com.gabo.ramo.core.BasePresenter
import com.gabo.ramo.core.CoroutineContextProvider
import com.gabo.ramo.data.Response
import com.gabo.ramo.domain.FindMoviesByQueryInteractor
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext

class MovieSearchPresenter(context: Context?) : BasePresenter<MovieSearchView>() {

    private val contextPool: CoroutineContextProvider = CoroutineContextProvider()
    private val findMoviesByQueryInteractor: FindMoviesByQueryInteractor = FindMoviesByQueryInteractor(context?.cacheDir)

    fun findMoviesBy(query: String) {
        GlobalScope.launch(contextPool.Main) {

            view?.startSearchQueryAnimation()

            val listOfMovies = withContext(contextPool.IO) {
                findMoviesByQueryInteractor.execute(query)
            }


            when (listOfMovies) {
                is Response.Success -> {
                    view?.updateListOfMovies(listOfMovies.data)
                    view?.showSearchingResults(listOfMovies.data.size)
                }
                is Response.Error -> view?.showErrorFetchingMovies(listOfMovies.errorMsg)
            }

            view?.stopSearchQueryAnimation()


        }

    }

}


