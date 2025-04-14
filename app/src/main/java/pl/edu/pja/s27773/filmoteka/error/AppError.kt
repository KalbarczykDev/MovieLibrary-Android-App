package pl.edu.pja.s27773.filmoteka.error

import pl.edu.pja.s27773.filmoteka.R

/**
 * Interface representing an application-level error with both a user-facing
 * message and a developer-friendly debug message.
 *
 * @property stringResKey Resource ID for the localized error message.
 * @property debugMessage Message used for debugging and logging.
 */

interface AppError {
    val stringResKey: Int
    val debugMessage: String
}

/**
 * Represents a generic, catch-all error used when the specific cause is unknown.
 *
 * @property stringResKey Resource ID for the error message.
 * @property debugMessage Developer-facing error message.
 */
sealed class DefaultAppError(
    override val stringResKey: Int,
    override val debugMessage: String
) : AppError {
    /** Default fallback error. */
    object Default : DefaultAppError(R.string.unknown_error, "Something Went Wrong")
}

/**
 * Exception class that wraps an [AppError] and exposes both debug and user-friendly messages.
 *
 * Can be thrown directly using an [AppError] instance.
 *
 * @constructor Creates an exception from a debug message and string resource key.
 * @param message Debug message describing the error.
 * @param stringResKey Resource ID for the user-facing error message.
 */
class AppErrorException(
    override val message: String,
    val stringResKey: Int
) : Exception(message) {
    /**
     * Creates an [AppErrorException] from an [AppError] object.
     *
     * @param error The [AppError] to wrap.
     */
    constructor(error: AppError) : this(error.debugMessage, error.stringResKey)
}
