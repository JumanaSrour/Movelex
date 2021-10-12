package com.example.newmovlex.ui.models

import com.example.newmovlex.ui.adapters.FavoriteAdapter.Companion.TYPE_ITEM
import java.io.Serializable


class FavoriteModel: Serializable {
    var id: Int = 0
    var imdbid: Int = 0
    var name: String = ""
    var image: String = ""
    var bio: String = ""
    var year: Int = 0
    var languages: String = ""
    var country: String = ""
    var director: String = ""
    var writer: String = ""
    var producer: String = ""
    var cast: String = ""
    var youtube_url: String = ""
    var review_counts: Int = 0
    var review= Review()
    var type: Int = TYPE_ITEM
}