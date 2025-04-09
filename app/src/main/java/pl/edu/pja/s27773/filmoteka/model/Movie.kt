package pl.edu.pja.s27773.filmoteka.model

import android.net.Uri
import androidx.annotation.StringRes
import pl.edu.pja.s27773.filmoteka.R
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


enum class Category(val stringResKey: String) {
    MOVIE("category_movie"),
    SERIES("category_series"),
    DOCUMENTARY("category_documentary"),
    NONE("category_all")
}

enum class Status(@StringRes val stringResId: Int) {
    WATCHED(R.string.status_watched),
    NOT_WATCHED(R.string.status_not_watched),
    NONE(R.string.status_all)
}










