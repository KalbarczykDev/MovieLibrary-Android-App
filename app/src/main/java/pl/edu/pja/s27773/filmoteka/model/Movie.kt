package pl.edu.pja.s27773.filmoteka.model

import android.net.Uri
import java.time.LocalDate

// data class = javowy record?
data class Movie(
    val id: Id,
    var title: Title,
    var releaseDate: ReleaseDate,
    var category: Category,
    var status: Status,
    var rating: Rating? = null,
    var comment: Comment? = null,
    var posterUri: Uri? = null
)



enum class Category {
    MOVIE, SERIES, DOCUMENTARY, OTHER
}

enum class Status {
    WATCHED, NOT_WATCHED
}











