package com.example.newmovlex.feature.movie.movieDetails.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.newmovlex.R
import com.example.newmovlex.feature.movie.displayFavorites.view.FavoriteMovieView
import com.example.newmovlex.feature.movie.displayFavorites.view.FavoritesPresenter
import com.example.newmovlex.feature.movie.displayMovies.view.HomeMoviesActivity
import com.example.newmovlex.feature.movie.movieDetails.presenter.MovieDetailsPresenter
import com.example.newmovlex.feature.user.login.view.LoginActivity
import com.example.newmovlex.network.Constants
import com.example.newmovlex.ui.adapters.MovieCastAdapter
import com.example.newmovlex.ui.adapters.MovieReviewAdapter
import com.example.newmovlex.ui.models.*
import com.example.newmovlex.utils.PaginationLinearScrollListener
import com.example.newmovlex.utils.storage.SharedPrefManager
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_movie_details.*

class MovieDetailsActivity : AppCompatActivity(), MovieDetailsView, FavoriteMovieView {
    private lateinit var favorite_movie : FavoriteModel
    private lateinit var movie: MovieModel
    private lateinit var movieReviewAdapter: MovieReviewAdapter
    private lateinit var favoritesPresenter: FavoritesPresenter
    private lateinit var mAppBarStateChangeListener: AppBarLayout
    private lateinit var movieCastAdapter: MovieCastAdapter
    private lateinit var presenter: MovieDetailsPresenter
    private var currentPage = Constants.FIRST_PAGE
    private var totalPages: Int = Constants.FIRST_PAGE
    private var isLastPage: Boolean = false
    private var isLoading: Boolean = false
    private lateinit var mGridLayoutManager: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        getIntentData()
        initPresenter()
        initMovieReviewAdapter()
        setAppBarChangeListener()
        setEventsListener()
        mGridLayoutManager = LinearLayoutManager(this);
        rv_reviews.layoutManager = mGridLayoutManager

        initPaginationListener(mGridLayoutManager);

    }

    private fun initPaginationListener(mLayoutManager: LinearLayoutManager) {
        rv_reviews.addOnScrollListener(object : PaginationLinearScrollListener(mLayoutManager) {
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
        currentPage = Constants.FIRST_PAGE;
        isLoading = true;
        getDataListFromServer()
    }

    private fun getDataListFromServer() {
        presenter.getMovieReviews(currentPage)
        /// getMovieReview when review item clicked
        presenter.getMovieReview()
//        favoritesPresenter.favNotFavMovie()

    }

    private fun getNextPage() {
        if (currentPage <= totalPages) {
            movieReviewAdapter.addLoadingFooter()
        } else {
            isLastPage = true
        }

        if (!isLastPage) {
            getDataListFromServer()
        }
    }

    companion object {
        fun newInstance(context: Context, movieModel: MovieModel): Intent {
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra("movie", movieModel)
            return intent
        }
    }

    fun getIntentData() {
        movie = intent.getSerializableExtra("movie") as MovieModel
    }

    private fun setAppBarChangeListener() {
        mAppBarStateChangeListener = appbar
        mAppBarStateChangeListener.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {
                Log.d("State", "")
                when (state) {
                    State.COLLAPSED -> {
                        movie_title.visibility = View.VISIBLE
                        img_back.visibility = View.VISIBLE
                        txt_back.visibility = View.INVISIBLE
                    }
                    State.EXPANDED -> {
                        movie_title.visibility = View.INVISIBLE
                        img_back.visibility = View.INVISIBLE
                        txt_back.visibility = View.VISIBLE
                    }
                    State.IDLE -> { /* Do something */
                    }
                }
            }
        }
        )
    }


    private fun initMovieReviewAdapter() {
        movieReviewAdapter = MovieReviewAdapter(this)
        Log.e("//***","initMoviesAdapter")
        rv_reviews.adapter = movieReviewAdapter

    }


    private fun initPresenter() {
        presenter = MovieDetailsPresenter(this, this)
        presenter.getMovieDetails(movie)
        llProgressBarDetails.visibility = View.VISIBLE
        rate.visibility = View.INVISIBLE
        tv_title.visibility = View.INVISIBLE
        tv_rdate.visibility = View.INVISIBLE
        tv_people.visibility = View.INVISIBLE
        tv_number.visibility = View.INVISIBLE
        tv_genre.visibility = View.INVISIBLE
        tv_count.visibility = View.INVISIBLE
        tv_cast.visibility = View.INVISIBLE
        tv_bio.visibility = View.INVISIBLE
        iv_back.visibility = View.INVISIBLE
        iv_poster.visibility = View.INVISIBLE
        img_back.visibility = View.INVISIBLE
        favoritesPresenter = FavoritesPresenter(this, this)
        favoritesPresenter.favNotFavMovie()
    }

    private fun setEventsListener() {
        btn_login_details.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        star_movie.setOnClickListener {
            favoritesPresenter.favNotFavMovie()
            star_movie.setImageResource(R.drawable.star_filled)
            Toast.makeText(this, "Successfully added to favorites", Toast.LENGTH_SHORT).show()
        }
        btn_back_movie.setOnClickListener {
            startActivity(Intent(this, HomeMoviesActivity::class.java))
        }

    }

    override fun returnMovie(movieModel: MovieModel) {
        llProgressBarDetails.visibility = View.GONE
        Toast.makeText(applicationContext, "$movieModel", Toast.LENGTH_SHORT).show()
        tv_title.visibility = View.VISIBLE
        tv_rdate.visibility = View.VISIBLE
        tv_people.visibility = View.VISIBLE
        tv_genre.visibility = View.VISIBLE
        tv_cast.visibility = View.VISIBLE
        tv_bio.visibility = View.VISIBLE
        iv_back.visibility = View.VISIBLE
        iv_poster.visibility = View.VISIBLE
        img_back.visibility = View.VISIBLE


        tv_bio.text = (movieModel.bio)
        tv_cast.text = (movieModel.cast)
        tv_genre.text = (movieModel.writer)
        Glide.with(this).load(movieModel.image).into(iv_poster)
        Glide.with(this).load(movieModel.image).into(iv_back)
        Glide.with(this).load(movieModel.image).into(img_back)
        tv_people.text = (movieModel.producer)
        tv_rdate.text = (movieModel.youtube_url)
        tv_title.text = (movieModel.name)
        txt_back.text = (movieModel.name)
        movie_title.text = (movieModel.name)

    }

    override fun returnReviews(review: ArrayList<Review>, num: Int) {
        totalPages = num
        isLoading = false
        rv_reviews.visibility = View.VISIBLE
//        llEmpty.visible=View.GONE
        if (movieReviewAdapter == null) initMovieReviewAdapter()
        if (movieReviewAdapter.itemCount > 0) {
            movieReviewAdapter.removeLoadingFooter()
        }
        if (currentPage == Constants.FIRST_PAGE) {
            movieReviewAdapter.removeAll()
        }
        movieReviewAdapter.addItems(review)
    }

    override fun returnReview(review: Review) {
        Log.d("---", "returnReview: $review ")
//        Toast.makeText(applicationContext, "$review", Toast.LENGTH_SHORT).show()
    }

    override fun showErrorMessage(message: String) {
        Log.d("---", "showErrorMessage: $message ")
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    override fun returnMovie(movie: ArrayList<MovieModel>, num: Int) {
        Toast.makeText(this, "$movie", Toast.LENGTH_SHORT).show()
    }

    override fun returnFavoriteMovie(favoriteModel: ArrayList<FavoriteModel>) {
        Toast.makeText(this, "$favoriteModel", Toast.LENGTH_SHORT).show()
    }


    override fun showErrorMsg(message: String?) {
        Log.d("---", "showErrorMessage: $message ")
//        Toast.makeText(this, "$message", Toast.LENGTH_SHORT).show()
    }

}