package com.example.newmovlex.ui.adapters

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newmovlex.R
import com.example.newmovlex.ui.models.Cast
import kotlinx.android.synthetic.main.cast_item.view.*

class MovieCastAdapter (var activity: Activity, var data: MutableList<Cast>)
    :RecyclerView.Adapter<MovieCastAdapter.ViewHolder>(){


         class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
            val castImageView: ImageView = itemView.iv_cast
             val castName:TextView = itemView.tv_cast_name


        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cast_item, parent, false)


        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)

        var width = displayMetrics.widthPixels

        var layoutParams = view.layoutParams
        layoutParams.width = ((width)/4.2).toInt()
        view.layoutParams = layoutParams

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.castName.text = data[position].castName
        holder.castImageView.setImageResource(data[position].castImageView)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}