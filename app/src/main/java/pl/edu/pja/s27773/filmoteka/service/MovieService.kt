package pl.edu.pja.s27773.filmoteka.service

import pl.edu.pja.s27773.filmoteka.error.AppErrorException
import pl.edu.pja.s27773.filmoteka.error.MovieCrudError
import pl.edu.pja.s27773.filmoteka.model.dto.MovieDto
import pl.edu.pja.s27773.filmoteka.repository.MovieRepository
import pl.edu.pja.s27773.filmoteka.mapper.toDomain
import pl.edu.pja.s27773.filmoteka.mapper.toDto
import pl.edu.pja.s27773.filmoteka.model.MovieCategory
import pl.edu.pja.s27773.filmoteka.model.MovieId
import pl.edu.pja.s27773.filmoteka.model.MovieStatus

/**
 * Service layer responsible for managing movie-related operations such as
 * fetching, adding, updating, and deleting movies.
 *
 * Applies validation rules before interacting with the repository.
 */
object MovieService {

    /**
     * Retrieves all movies, sorted by release date.
     *
     * @return A list of movies as [MovieDto] objects.
     */
    fun getAll(): List<MovieDto> {
        return MovieRepository.getAll()
            .sortedBy { it.releaseDate.toString() }
            .map { it.toDto() }
    }

    /**
     * Retrieves a movie by its ID.
     *
     * @param id The ID of the movie to fetch.
     * @return The [MovieDto] if found, or null if the ID is invalid or not found.
     */
    fun getById(id: Int): MovieDto? {
        if (id < 0) return null
        return MovieRepository.getById(MovieId.of(id))?.toDto()
    }

    /**
     * Adds a new movie to the repository after validating the input.
     *
     * @param dto The movie data to add.
     * @throws AppErrorException If validation fails.
     */
    fun add(dto: MovieDto) {
        validate(dto)
        MovieRepository.add(dto.toDomain())
    }

    /**
     * Deletes a movie from the repository.
     *
     * @param dto The movie to delete.
     */
    fun delete(dto: MovieDto) {
        MovieRepository.delete(dto.toDomain())
    }

    /**
     * Updates an existing movie after validating the input.
     *
     * @param dto The movie data to update.
     * @throws AppErrorException If validation fails.
     */
    fun update(dto: MovieDto) {
        validate(dto)
        MovieRepository.update(dto.toDomain())
    }


    /**
     * Validates the provided movie data.
     *
     * Ensures required fields like category, status, and rating (if watched) are properly set.
     *
     * @param dto The movie to validate.
     * @throws AppErrorException If validation fails.
     */
    private fun validate(dto: MovieDto) {
        if (dto.category == MovieCategory.NONE) {
            throw AppErrorException(MovieCrudError.CategoryNotSelected)
        }

        if (dto.status == MovieStatus.NONE) {
            throw AppErrorException(MovieCrudError.StatusNotSelected)
        }

        if (dto.status == MovieStatus.WATCHED && dto.rating == null) {
            throw AppErrorException(MovieCrudError.RatingNotSetForWatchedMovie)
        }
    }


}
