package com.example.newmovlex.ui.models

import java.io.Serializable


data class MovieList(
    val data: ArrayList<MovieModel>,
    val total_records: Int
): Serializable{
}