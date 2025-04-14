package pl.edu.pja.s27773.filmoteka.repository

import pl.edu.pja.s27773.filmoteka.error.AppErrorException
import pl.edu.pja.s27773.filmoteka.error.MovieCrudError
import pl.edu.pja.s27773.filmoteka.model.*

/**
 * In-memory repository for storing and managing movies.
 *
 * Provides basic CRUD operations and utility methods for interacting with the movie collection.
 */
object MovieRepository {
    private val movies = mutableListOf<Movie>()

    /**
     * Returns a copy of the current list of all movies.
     *
     * @return A list of [Movie] objects.
     */
    fun getAll(): List<Movie> {
        return movies.toList()
    }

    /**
     * Retrieves a movie by its ID.
     *
     * @param id The [MovieId] of the movie to fetch.
     * @return The matching [Movie], or null if not found.
     */
    fun getById(id: MovieId): Movie? {
        return movies.find { it.id == id }
    }

    /**
     * Adds a new movie to the repository.
     *
     * @param movie The [Movie] to add.
     * @throws AppErrorException If a movie with the same ID already exists.
     */
    fun add(movie: Movie) {
        if (movies.any { it.id == movie.id }) throw AppErrorException(MovieCrudError.IdTaken)
        movies.add(movie)
    }

    /**
     * Generates the next available movie ID.
     *
     * @return A new unique [MovieId].
     */
    fun nextId(): MovieId {
        val maxId = movies.maxOfOrNull { it.id.value } ?: 0
        return MovieId.of(maxId + 1)
    }

    /**
     * Updates an existing movie in the repository.
     *
     * @param updated The movie with updated data.
     * @throws AppErrorException If the movie is not found.
     */
    fun update(updated: Movie) {
        val index = movies.indexOfFirst { it.id == updated.id }
        if (index == -1) {
            throw AppErrorException(MovieCrudError.NotFound)
        } else {
            val movie = movies[index]
            movie.title = updated.title
            movie.releaseDate = updated.releaseDate
            movie.status = updated.status
            movie.category = updated.category
            movie.rating = updated.rating
            movie.posterUri = updated.posterUri
        }
    }


    /**
     * Deletes the specified movie from the repository.
     *
     * @param movie The movie to remove.
     * @throws AppErrorException If the movie is not found.
     */
    fun delete(movie: Movie) {
        val removed = movies.removeIf { it.id == movie.id }
        if (!removed) throw AppErrorException(MovieCrudError.NotFound)
    }

    /**
     * Clears all movies from the repository.
     */
    fun clear() {
        movies.clear()
    }



}


