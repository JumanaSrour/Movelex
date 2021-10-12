package com.example.newmovlex.feature.user.profile.view

import android.annotation.SuppressLint
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
import com.example.newmovlex.feature.app.settings.view.SettingsActivity
import com.example.newmovlex.feature.movie.displayFavorites.view.FavoriteMovieView
import com.example.newmovlex.feature.movie.displayFavorites.view.FavoritesPresenter
import com.example.newmovlex.feature.movie.displayMovies.view.HomeMoviesActivity
import com.example.newmovlex.feature.movie.movieDetails.view.MovieDetailsActivity
import com.example.newmovlex.feature.user.profile.presenter.ProfilePresenter
import com.example.newmovlex.network.Constants
import com.example.newmovlex.ui.adapters.FavoriteAdapter
import com.example.newmovlex.ui.adapters.MovieAdapter
import com.example.newmovlex.ui.models.FavoriteModel
import com.example.newmovlex.ui.models.FavoriteMovieList
import com.example.newmovlex.ui.models.MovieModel
import com.example.newmovlex.ui.models.User
import com.example.newmovlex.utils.PaginationLinearScrollListener
import com.example.newmovlex.utils.storage.SharedPrefManager
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity(), ProfileView, FavoriteMovieView, FavoriteAdapter.SetClickListener,
    MovieAdapter.SetClickListener {
    private lateinit var presenter: ProfilePresenter
    private lateinit var mPresenter: FavoritesPresenter
    private lateinit var adapter: FavoriteAdapter
    private var currentPage = Constants.FIRST_PAGE
    private var totalPages: Int = Constants.FIRST_PAGE
    private var isLastPage: Boolean = false
    private var isLoading: Boolean = false
    private lateinit var mLayoutManager: GridLayoutManager
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        initPresenter()
        initFavoritePresenter()
        setEventsListener()
        initFavoriteMoviesAdapter()
        mLayoutManager = GridLayoutManager(this, 2);
        rv_favortie.layoutManager=mLayoutManager
        initPaginationListener(mLayoutManager)
    }

    private fun initFavoritePresenter() {
        mPresenter = FavoritesPresenter(this, this)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initPaginationListener(mLayoutManager: GridLayoutManager) {
        rv_favortie.addOnScrollListener(object : PaginationLinearScrollListener(mLayoutManager) {
            @RequiresApi(Build.VERSION_CODES.M)
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

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getFirstPage() {
        currentPage = Constants.FIRST_PAGE;
        isLoading = true;
        getDataListFromServer()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getDataListFromServer() {
        mPresenter = FavoritesPresenter(this, this)
        mPresenter.getListOfFavoriteMovies(currentPage)

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getNextPage() {
        if (currentPage <= totalPages) {
            adapter.addLoadingFooter()
        } else {
            isLastPage = true
        }

        if (!isLastPage) {
            getDataListFromServer()
        }
    }

    private fun setEventsListener() {
       ib_settings.setOnClickListener {
           startActivity(Intent(this, SettingsActivity::class.java))
       }
        btn_back_profile.setOnClickListener {
            startActivity(Intent(this, HomeMoviesActivity::class.java))
        }

    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.M)
    private fun initPresenter() {
        presenter = ProfilePresenter(this, this)
        presenter.getUserProfile()
        tv_username.text = (SharedPrefManager.user.name)
    }

    private fun initFavoriteMoviesAdapter() {
        adapter = FavoriteAdapter(this)
        adapter.setListener(this)
        rv_favortie.adapter = adapter
    }



    override fun returnUser(user: User) {
        Log.d("---", "returnUser: $user")
    }

    override fun showErrorMsg(msg: String?) {
        Toast.makeText(applicationContext, "$msg", Toast.LENGTH_SHORT).show()
    }


    override fun returnMovie(movie: ArrayList<MovieModel>, num: Int) {
        totalPages = num
        isLoading = false
        rv_favortie.visibility = View.VISIBLE
        if (adapter == null) initFavoriteMoviesAdapter()
        if (adapter.itemCount > 0) {
            adapter.removeLoadingFooter()
        }
        if (currentPage == Constants.FIRST_PAGE) {
            adapter.removeAll()
        }
        adapter.addItems(movie)
    }

    override fun returnFavoriteMovie(favoriteModel: ArrayList<FavoriteModel>) {
        Toast.makeText(applicationContext, "$favoriteModel", Toast.LENGTH_SHORT).show()
    }

    override fun onItemClickListener(position: Int, movie: MovieModel) {
        startActivity(MovieDetailsActivity.newInstance(this, movie))
    }
}