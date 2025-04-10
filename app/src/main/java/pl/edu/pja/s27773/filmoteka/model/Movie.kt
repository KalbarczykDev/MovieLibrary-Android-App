package pl.edu.pja.s27773.filmoteka.model

import android.net.Uri
import androidx.annotation.StringRes
import pl.edu.pja.s27773.filmoteka.R

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


enum class Category(@StringRes val stringResId: Int) {
    MOVIE(R.string.category_movie),
    SERIES(R.string.category_series),
    DOCUMENTARY(R.string.category_documentary),
    NONE(R.string.category_none)
}

enum class Status(@StringRes val stringResId: Int) {
    WATCHED(R.string.status_watched),
    NOT_WATCHED(R.string.status_not_watched),
    NONE(R.string.status_none)
}










