package com.gabo.ramo.presentation.moviedetail


import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

import com.gabo.ramo.R
import com.gabo.ramo.data.Movie
import kotlinx.android.synthetic.main.fragment_movie_detail.*

const val MOVIE_ID_PARAM = "movie_id"

class MovieDetailFragment : Fragment(), MovieDetailScreen {

    companion object {
        @JvmStatic
        fun newInstance(arguments: Bundle): MovieDetailFragment {
            val fragment = MovieDetailFragment()
            fragment.arguments = arguments
            return fragment
        }
    }

    private var movieId: String? = null
    lateinit var presenter: MovieDetailPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_movie_detail, container, false)
        presenter = MovieDetailPresenter()
        presenter.attachView(this)
        arguments?.let {
            movieId = it.getString(MOVIE_ID_PARAM)
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        presenter.attachView(this)
        movieId?.let {
            presenter.init(it)
        }

    }

    override fun onPause() {
        super.onPause()
        presenter.detachView()
    }

    override fun renderMovieDetail(movie: Movie?) {
        movie?.let {
            val uri = it.posterPath?.let { Uri.parse(it) }
            Glide.with(fragment_movie_detail)
                    .load(uri)
                    .apply(RequestOptions()
                            .fitCenter()
                            .override(631, 825)
                            .placeholder(R.drawable.no_image_found)
                            .error(R.drawable.no_image_found)
                    )
                    .into(movie_poster)
            movie_title.text = it.title
            movie_overview.text = it.overview

        }

    }
}
