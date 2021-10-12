package com.example.newmovlex.ui.models

import com.example.newmovlex.ui.adapters.MovieReviewAdapter
import java.io.Serializable


class Review :Serializable{
    var id: Int = 0
    var text: String = ""
    var user = User()
    var children = ArrayList<Review>()
    var type: Int = MovieReviewAdapter.TYPE_ITEM

}