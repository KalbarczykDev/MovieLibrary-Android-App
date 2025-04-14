package pl.edu.pja.s27773.filmoteka.model.dto

import android.net.Uri
import pl.edu.pja.s27773.filmoteka.model.MovieCategory
import pl.edu.pja.s27773.filmoteka.model.MovieStatus
import java.time.LocalDate

data class MovieDto (
    val id: Int?,
    val title: String,
    val releaseDate: LocalDate,
    val category: MovieCategory,
    val status: MovieStatus,
    val rating: Float?,
    val posterUri: Uri?
)