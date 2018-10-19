package com.gabo.ramo.presentation.search

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabo.ramo.R
import com.gabo.ramo.core.BaseView
import com.gabo.ramo.core.extensions.onChange
import com.gabo.ramo.data.Movie
import com.gabo.ramo.presentation.moviedetail.MOVIE_ID_PARAM
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_movie_search.*


class MovieSearchFragment : Fragment(), MovieSearchView, MovieRecyclerViewAdapter.OnListInteractionListener {


    companion object {
        @JvmStatic
        fun newInstance(arguments: Bundle): MovieSearchFragment {
            val fragment = MovieSearchFragment()
            fragment.arguments = arguments
            return fragment
        }
    }

    lateinit var searchPresenter: MovieSearchPresenter
    lateinit var movieAdapter: MovieRecyclerViewAdapter
    private var listener: BaseView.NavigationListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    fun onQueryReceived(query: String?) {
        query?.let { searchPresenter?.findMoviesBy(query.trim()) }
    }

    override fun onResume() {
        super.onResume()
        searchPresenter?.attachView(this)

    }

    override fun onPause() {
        super.onPause()
        searchPresenter?.detachView()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_movie_search, container, false)
        movieAdapter = MovieRecyclerViewAdapter(listOf(), this)
        view.findViewById<RecyclerView>(R.id.list)?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = movieAdapter
        }
        searchPresenter = MovieSearchPresenter(context)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        autocompleteSearch.onChange {
            it?.let {
                when (it.length) {
                    0 -> {
                        movieAdapter.clearMovies()
                        stopSearchQueryAnimation()
                    }
                    1, 2 -> startSearchQueryAnimation()
                    else -> onQueryReceived(it)

                }
            }

        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseView.NavigationListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun updateListOfMovies(movies: List<Movie>) {
        movieAdapter.updateMovies(movies)
    }

    override fun showErrorFetchingMovies(errorMsg: String) {
        view?.let {
            Snackbar.make(it, errorMsg, Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onListItemSelected(item: Movie) {
        var params = bundleOf(MOVIE_ID_PARAM to item.id)
        listener?.navigateTo(R.id.fragment_movie_detail, params)

    }

    override fun startSearchQueryAnimation() {
        loading_animation.visibility = View.VISIBLE
        list.visibility = View.GONE
        loading_animation.setAnimation(R.raw.search_ask_loop)
        loading_animation.playAnimation()
    }

    override fun stopSearchQueryAnimation() {
        loading_animation.pauseAnimation()
        loading_animation.visibility = View.GONE
        list.visibility = View.VISIBLE
    }

    override fun showModeSearchingResults(resultsSize: Int) {
        Snackbar.make(list, "Search results ($resultsSize)", Snackbar.LENGTH_LONG).show()
    }

}
