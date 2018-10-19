package com.gabo.ramo.presentation

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.gabo.ramo.R
import com.gabo.ramo.core.BaseView
import com.gabo.ramo.core.extensions.addFragment
import com.gabo.ramo.core.extensions.replaceFragment
import com.gabo.ramo.presentation.moviedetail.MovieDetailFragment
import com.gabo.ramo.presentation.search.MovieSearchFragment

class EntryPointActivity : AppCompatActivity(), BaseView.NavigationListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.entry_point_layout)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        findViewById<FrameLayout>(R.id.fragment_container)?.let {
            if (savedInstanceState == null) {
                navigateTo(R.id.fragment_movie_search, Bundle())
            }
        }

    }

    override fun navigateTo(framentId: Int, data: Bundle) {
        when(framentId){
            R.id.fragment_movie_search -> {
                addFragment(MovieSearchFragment.newInstance(data), R.id.fragment_container)
            }
            R.id.fragment_movie_detail -> replaceFragment(MovieDetailFragment.newInstance(data), R.id.fragment_container)
            else -> replaceFragment(MovieSearchFragment.newInstance(data), R.id.fragment_container)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (supportFragmentManager.backStackEntryCount == 0 ) finish()
    }

}
