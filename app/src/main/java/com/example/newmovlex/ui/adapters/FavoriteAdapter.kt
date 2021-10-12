package com.example.newmovlex.ui.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newmovlex.R
import com.example.newmovlex.ui.models.FavoriteModel
import com.example.newmovlex.ui.models.MovieModel
import kotlinx.android.synthetic.main.favorite_item.view.*

class FavoriteAdapter(var mActivity: Activity) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var favoriteMovies = ArrayList<MovieModel>()
    private  var listener: MovieAdapter.SetClickListener? = null
    private var isLoadingAdded = false

    init {
        favoriteMovies = ArrayList()
    }

    override fun getItemViewType(position: Int): Int {
        return if (favoriteMovies[position].type == TYPE_LAST_ITEM) TYPE_LAST_ITEM else TYPE_ITEM
    }

    inner class MyViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        var movieId: Int = 0
        val movieName: TextView = itemView.movie_name_favorite
        val movieImage: ImageView = itemView.movie_image_favorite
        val cardItem: CardView = itemView.cv_movie_favorite

        fun setData(item: MovieModel, position: Int) {
            movieName.text = item.name
            movieId = item.id
            Glide.with(mActivity).load(item.image).into(movieImage)
            cardItem.setOnClickListener {
                if (listener != null) listener?.onItemClickListener(position, item)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder
        val view: View
        when (viewType) {
            TYPE_ITEM -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.favorite_item, parent, false)
                viewHolder = MyViewHolder(view)
            }
            TYPE_LAST_ITEM -> {
                view =
                    LayoutInflater.from(parent.context).inflate(R.layout.load_more, parent, false)
                viewHolder = LoadingVH(view)
            }
            else -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.favorite_item, parent, false)
                viewHolder = MyViewHolder(view)
            }
        }
        return viewHolder
    }


    override fun getItemCount(): Int {
        return favoriteMovies.size
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        when (getItemViewType(position)) {
            TYPE_ITEM -> {
                val viewHolder = holder as MyViewHolder
                viewHolder.setData(getItem(position), position)
            }
        }
    }



    fun add(favorites: MovieModel) {
        favoriteMovies.add(favorites)
        notifyItemInserted(favoriteMovies.size - 1)
    }


    fun addItems(favoriteList: List<MovieModel>) {
        for (mc in favoriteList) {
            add(mc)
        }
    }

    fun remove(favorites: MovieModel) {
        val position = favoriteMovies.indexOf(favorites)
        if (position >= favoriteMovies.size) {
            favoriteMovies.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeAll() {
        this.favoriteMovies.clear()
        notifyDataSetChanged()

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setListener(listener: MovieAdapter.SetClickListener) {
        this.listener = listener
        Log.e("//***","listener")
        notifyDataSetChanged()
    }

    fun clear() {
        isLoadingAdded = false
        while (getItemCount() > 0) {
            remove(favoriteMovies[0])
        }
    }

    fun isEmpty(): Boolean {
        return getItemCount() == 0
    }

    fun addLoadingFooter() {
        if (!isLoadingAdded) {
            isLoadingAdded = true
            add()
        }
    }

    fun add() {
        val item = MovieModel()
        item.type = TYPE_LAST_ITEM
        favoriteMovies.add(item)
        notifyDataSetChanged()
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false
        val position = favoriteMovies.size - 1
        val movieItem = getItem(position)
        if (movieItem != null) {
            favoriteMovies.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun getItem(position: Int): MovieModel {
        return favoriteMovies.get(position)
    }

    companion object {
        const val TYPE_ITEM = 0
        const val TYPE_LAST_ITEM = 1
    }

    inner class LoadingVH internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView)

    //    class LoadingVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    public fun loadingVH(itemView: View): View {
        return (itemView)
    }
    interface SetClickListener {
        fun onItemClickListener(position: Int, movie: MovieModel);
    }
}