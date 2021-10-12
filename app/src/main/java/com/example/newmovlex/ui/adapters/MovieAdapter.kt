package com.example.newmovlex.ui.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newmovlex.R
import com.example.newmovlex.ui.models.MovieModel
import kotlinx.android.synthetic.main.movie_item.view.*

class MovieAdapter(
    var activity: Activity
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var movies = ArrayList<MovieModel>()
    private  var listener: SetClickListener? = null
    private var isLoadingAdded = false

    init {
        movies = ArrayList()
//        listener = this.listener
    }

    override fun getItemViewType(position: Int): Int {
        return if (movies[position].type == TYPE_LAST_ITEM) TYPE_LAST_ITEM else TYPE_ITEM
    }

    inner class MyViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val movieName: TextView = itemView.movie_name
        val movieImage: ImageView = itemView.movie_image

        val cardItem: CardView = itemView.cv_movie

        fun setData(item: MovieModel, position: Int) {
            itemView.setOnClickListener {
                Log.e("//***","llllllllll")
            }
            cardItem.setOnClickListener {
                Log.e("//***","llllllllll")
                if (listener != null) listener?.onItemClickListener(position, item)
            }

            movieName.text = item.name
            val movieId = item.id
            val movieImdbId = item.imdbid
            Glide.with(activity).load(item.image).into(movieImage)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder
        val view: View
        when (viewType) {
            TYPE_ITEM -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.movie_item, parent, false)
                viewHolder = MyViewHolder(view)
            }
            TYPE_LAST_ITEM -> {
                view =
                    LayoutInflater.from(parent.context).inflate(R.layout.load_more, parent, false)
                viewHolder = LoadingVH(view)
            }
            else -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.movie_item, parent, false)
                viewHolder = MyViewHolder(view)
            }
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return movies.size
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

    fun add(movie: MovieModel) {
        movies.add(movie)
        notifyItemInserted(movies.size - 1)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setListener(listener: SetClickListener) {
        this.listener = listener
        Log.e("//***","listener")
        notifyDataSetChanged()
    }

    fun addItems(movieList: List<MovieModel>) {
        for (mc in movieList) {
            add(mc)
        }
    }

    fun remove(movie: MovieModel) {
        val position = movies.indexOf(movie)
        if (position >= movies.size) {
            movies.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeAll() {
        this.movies.clear()
        notifyDataSetChanged()

    }

    fun clear() {
        isLoadingAdded = false
        while (getItemCount() > 0) {
            remove(movies[0])
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
        movies.add(item)
        notifyDataSetChanged()
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false
        val position = movies.size - 1
        val movieItem = getItem(position)
        if (movieItem != null) {
            movies.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun getItem(position: Int): MovieModel {
        return movies.get(position)
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
