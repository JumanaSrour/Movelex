package com.example.newmovlex.ui.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newmovlex.R
import com.example.newmovlex.ui.models.Review
import kotlinx.android.synthetic.main.review_item.view.*

class MovieReviewAdapter(var activity: Activity)
    : RecyclerView.Adapter<MovieReviewAdapter.ViewHolder>() {
    private var reviews = ArrayList<Review>()
    private var listener: MovieReviewAdapter.SetClickListener? = null
    private var isLoadingAdded = false

    init {
        reviews = ArrayList()
    }
    override fun getItemViewType(position: Int): Int {
        return if (reviews[position].type == MovieReviewAdapter.TYPE_LAST_ITEM) MovieReviewAdapter.TYPE_LAST_ITEM else MovieReviewAdapter.TYPE_ITEM
    }

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
        val reviewUser : TextView = itemView.tv_username
        val reviewDesc : TextView = itemView.tv_desc
        fun setData(item: Review, position: Int) {
            itemView.setOnClickListener {
                Log.e("//***","llllllllll")
                if (listener != null) listener?.onItemClickListener(position, item)
            }
            reviewDesc.text = item.text
            reviewUser.text = item.user.name


        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder: RecyclerView.ViewHolder
        val view: View
        when (viewType) {
            MovieReviewAdapter.TYPE_ITEM -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.review_item, parent, false)
                viewHolder = ViewHolder(view)
            }
            MovieReviewAdapter.TYPE_LAST_ITEM -> {
                view =
                    LayoutInflater.from(parent.context).inflate(R.layout.load_more, parent, false)
                viewHolder = ViewHolder(view)
            }
            else -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.review_item, parent, false)
                viewHolder = ViewHolder(view)
            }
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return reviews.size
    }


    fun add(movie: Review) {
        reviews.add(movie)
        notifyItemInserted(reviews.size - 1)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setListener(listener: MovieReviewAdapter.SetClickListener) {
        this.listener = listener
        Log.e("//***","listener")
        notifyDataSetChanged()
    }

    fun addItems(reviewList: List<Review>) {
        for (rl in reviewList) {
            add(rl)
        }
    }

    fun remove(review: Review) {
        val position = reviews.indexOf(review)
        if (position >= reviews.size) {
            reviews.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeAll() {
        this.reviews.clear()
        notifyDataSetChanged()

    }

    fun clear() {
        isLoadingAdded = false
        while (getItemCount() > 0) {
            remove(reviews[0])
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
        val item = Review()
        item.type = MovieAdapter.TYPE_LAST_ITEM
        reviews.add(item)
        notifyDataSetChanged()
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false
        val position = reviews.size - 1
        val reviewItem = getItem(position)
        if (reviewItem != null) {
            reviews.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun getItem(position: Int): Review {
        return reviews.get(position)
    }
    companion object {
        const val TYPE_ITEM = 0
        const val TYPE_LAST_ITEM = 1
    }
    inner class LoadingVH internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView)

    interface SetClickListener {
        fun onItemClickListener(position: Int, review: Review)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            MovieReviewAdapter.TYPE_ITEM -> {
                val viewHolder = holder as MovieReviewAdapter.ViewHolder
                viewHolder.setData(getItem(position), position)
            }
        }
    }

}
