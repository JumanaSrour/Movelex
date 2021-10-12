package com.example.newmovlex.ui.models


data class Movie (
     // create new movie class with the new params from api
     val movieId: Int,
     val movieImage: Int,
     val movieName: String,
     val movie_rate: Int,
     val movie_rate_number: String,
     val movie_votes: String,

){}

