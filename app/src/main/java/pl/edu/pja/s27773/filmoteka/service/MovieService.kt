package pl.edu.pja.s27773.filmoteka.service

import pl.edu.pja.s27773.filmoteka.model.*
import pl.edu.pja.s27773.filmoteka.repository.MovieRepository

object MovieService {

    data class Filter(val category: Category? = null, val status: Status? = null)

    fun getFilteredAndSorted(filter: Filter): List<Movie> {
        return MovieRepository.getAll()
            .filter { movie ->
                (filter.category == null || movie.category == filter.category) &&
                (filter.status == null || movie.status == filter.status)
            }
            .sortedBy { it.releaseDate.toString() }
    }

    fun getSummaryCount(filter: Filter): Int {
        return getFilteredAndSorted(filter).size
    }

    fun onMovieClick(movie: Movie): Boolean {
        return movie.status == Status.NOT_WATCHED
    }

    fun deleteMovieById(id: Id): Boolean {
        return MovieRepository.deleteById(id) == null
    }

    fun addMovie(movie: Movie): Boolean {
        return MovieRepository.add(movie) == null
    }

    fun updateMovie(updated: Movie): Boolean {
        return MovieRepository.update(updated) == null
    }

    fun getNextId(): Id = MovieRepository.nextId()

    fun canEdit(movie: Movie): Boolean = movie.status == Status.NOT_WATCHED
}