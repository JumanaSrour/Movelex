package com.example.newmovlex.feature.movie.displayMovies.view

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newmovlex.R
import com.example.newmovlex.feature.movie.displayMovies.presenter.MoviePresenter
import com.example.newmovlex.feature.movie.movieDetails.view.MovieDetailsActivity
import com.example.newmovlex.feature.movie.searchMovie.presenter.SearchMoviePresenter
import com.example.newmovlex.feature.movie.searchMovie.view.SearchMovieView
import com.example.newmovlex.feature.user.login.view.LoginActivity
import com.example.newmovlex.feature.user.profile.view.ProfileActivity
import com.example.newmovlex.network.Constants.Companion.FIRST_PAGE
import com.example.newmovlex.ui.adapters.MovieAdapter
import com.example.newmovlex.ui.models.CustomDialog
import com.example.newmovlex.ui.models.MovieModel
import com.example.newmovlex.ui.models.SearchModel
import com.example.newmovlex.utils.AppSharedFunctions
import com.example.newmovlex.utils.PaginationLinearScrollListener
import com.example.newmovlex.utils.storage.SharedPrefManager
import kotlinx.android.synthetic.main.activity_home_movies.*
import kotlinx.android.synthetic.main.movie_item.*
import kotlinx.android.synthetic.main.search_item.*

class HomeMoviesActivity : AppCompatActivity(), MovieView, SearchMovieView,
    MovieAdapter.SetClickListener {
    private lateinit var adapter: MovieAdapter
    private lateinit var presenter: MoviePresenter
    private lateinit var mPresenter: SearchMoviePresenter
    private var currentPage = FIRST_PAGE
    private var totalPages: Int = FIRST_PAGE
    private var isLastPage: Boolean = false
    private var isLoading: Boolean = false
    private lateinit var mLayoutManager: GridLayoutManager

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_movies)
        adapter = MovieAdapter(this)
        setEventsListener()
//        initProperties()
        initPresenter()
        initMoviesAdapter()
        mLayoutManager = GridLayoutManager(this, 2);
        rv_movies.layoutManager = mLayoutManager
        initPaginationListener(mLayoutManager);
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initPresenter() {
        presenter = MoviePresenter(this, this)
        mPresenter = SearchMoviePresenter(this, this)
    }

    private fun setEventsListener() {
        ib_logout.setOnClickListener {
            createDialog()
        }
        iv_profile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        ed_search.setOnEditorActionListener { textView, i, keyEvent ->
            getFirstPage()
             false
        }
    }

    private fun createDialog() {
        val dialog = CustomDialog().newInstance(
            getString(R.string.log_out),
            getString(R.string.are_you_sure_logout),
            getString(R.string.accept),
            getString(R.string.cancel)
        )
        dialog.show(supportFragmentManager, "CustomDialogFragment")
        dialog.onClickListener(object : CustomDialog.CustomDialogListener {
            override fun onDialogPositiveClick(str: String) {
                SharedPrefManager.clear()
                dialog.dismiss()
                startActivity(Intent(this@HomeMoviesActivity, LoginActivity::class.java))
            }

            override fun onDialogNegativeClick(str: String) {
                dialog.dismiss()
            }

        })
    }

    private fun initPaginationListener(layoutManager: GridLayoutManager) {
        rv_movies.addOnScrollListener(object : PaginationLinearScrollListener(layoutManager) {
            override fun loadMoreItems() {
                isLoading = true
                currentPage += 1
                getNextPage()
            }

            override fun setEnabled(refresh: Boolean) {
                // for swipLayout
            }

            override fun _isLastPage(): Boolean {
                return isLastPage
            }

            override fun _isLoading(): Boolean {
                return isLoading
            }

        })
        getFirstPage()
    }

    private fun getFirstPage() {
        currentPage = FIRST_PAGE;
        isLoading = true;
        getDataListFromServer()
    }

    private fun getDataListFromServer() {
        presenter.getListOfMovies(currentPage, AppSharedFunctions.getStringFromEditText(ed_search))
    }

    fun getNextPage() {
        if (currentPage <= totalPages) {
            adapter.addLoadingFooter()
        } else {
            isLastPage = true
        }

        if (!isLastPage) {
            getDataListFromServer()
        }
    }

    private fun initMoviesAdapter() { // init adapter and add list of movies to adapter
        adapter = MovieAdapter(this)
        Log.e("//***", "initMoviesAdapter")
        adapter.setListener(this)
        rv_movies.adapter = adapter

    }


    override fun returnMovie(movie: ArrayList<MovieModel>, num: Int) {
        totalPages = num
        isLoading = false
        rv_movies.visibility = View.VISIBLE
        if (adapter == null) initMoviesAdapter()
        if (adapter.itemCount > 0) {
            adapter.removeLoadingFooter()
        }
        if (currentPage == FIRST_PAGE) {
            adapter.removeAll()
        }
        adapter.addItems(movie)
    }

    override fun returnMovie(movie: SearchModel) {
        Toast.makeText(applicationContext, "$movie", Toast.LENGTH_SHORT).show()
    }

    override fun showErrorMessage(message: String?) {
        Toast.makeText(applicationContext, "$message", Toast.LENGTH_SHORT).show()
    }

    override fun onItemClickListener(position: Int, movie: MovieModel) {
        Log.e("//***", "onItemClickListener")
        startActivity(MovieDetailsActivity.newInstance(this, movie))
    }
}

