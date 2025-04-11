package pl.edu.pja.s27773.filmoteka.model.dto

import android.net.Uri
import pl.edu.pja.s27773.filmoteka.model.Category
import pl.edu.pja.s27773.filmoteka.model.Status
import java.time.LocalDate

data class MovieDto (
    val id: Int?, // null if new
    val title: String,
    val releaseDate: LocalDate,
    val category: Category,
    val status: Status,
    val rating: Float?, // nullable for NOT_WATCHED
    val posterUri: Uri?
)