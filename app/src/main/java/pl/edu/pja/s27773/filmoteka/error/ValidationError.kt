package pl.edu.pja.s27773.filmoteka.error


interface ValidationError {
    val stringResKey: String

    companion object {
        const val DEFAULT = "error_default"
    }
}


enum class TitleError(override val stringResKey: String) : ValidationError {
    BLANK("error_title_blank"),
    TOO_LONG("error_title_to_long")
}

enum class RatingError(override val stringResKey: String) : ValidationError {
    INVALID("error_rating_invalid"),
    MISSING_FOR_WATCHED("error_rating_required")
}

enum class CommentError(override val stringResKey: String) : ValidationError {
    TOO_LONG("error_comment_too_long"),
    BLANK("error_comment_blank")
}

enum class IdError(override val stringResKey: String) : ValidationError {
    INVALID("error_id_invalid")
}