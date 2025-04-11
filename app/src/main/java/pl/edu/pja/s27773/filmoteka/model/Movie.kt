package pl.edu.pja.s27773.filmoteka.model

import android.net.Uri
import androidx.annotation.StringRes
import pl.edu.pja.s27773.filmoteka.R

// data class = javowy record?
data class Movie(
    val id: MovieId,
    var title: MovieTitle,
    var releaseDate: MovieReleaseDate,
    var category: MovieCategory,
    var status: MovieStatus,
    var rating: MovieRating? = null,
    var posterUri: Uri? = null
)


enum class MovieCategory(@StringRes val stringResId: Int) {
    MOVIE(R.string.category_movie),
    SERIES(R.string.category_series),
    DOCUMENTARY(R.string.category_documentary),
    NONE(R.string.category_none)
}

enum class MovieStatus(@StringRes val stringResId: Int) {
    WATCHED(R.string.status_watched),
    NOT_WATCHED(R.string.status_not_watched),
    NONE(R.string.status_none)
}










