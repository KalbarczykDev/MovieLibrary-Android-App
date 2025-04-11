package pl.edu.pja.s27773.filmoteka.service

import android.util.Log
import pl.edu.pja.s27773.filmoteka.error.AppErrorException
import pl.edu.pja.s27773.filmoteka.error.MovieCrudError
import pl.edu.pja.s27773.filmoteka.model.dto.MovieDto
import pl.edu.pja.s27773.filmoteka.repository.MovieRepository
import pl.edu.pja.s27773.filmoteka.mapper.toDomain
import pl.edu.pja.s27773.filmoteka.mapper.toDto
import pl.edu.pja.s27773.filmoteka.model.MovieCategory
import pl.edu.pja.s27773.filmoteka.model.MovieId
import pl.edu.pja.s27773.filmoteka.model.MovieStatus

object MovieService {
    fun getAll(): List<MovieDto> {
        return MovieRepository.getAll()
            .sortedBy { it.releaseDate.toString() }
            .map { it.toDto() }
    }

    fun getById(id: Int): MovieDto? {
        if (id < 0) return null
        return MovieRepository.getById(MovieId.of(id))?.toDto()
    }

    fun add(dto: MovieDto) {
        debug(dto)
        validate(dto)
        MovieRepository.add(dto.toDomain())
    }

    fun delete(dto: MovieDto) {
        MovieRepository.delete(dto.toDomain())
    }

    fun update(dto: MovieDto) {
        debug(dto)
        validate(dto)
        MovieRepository.update(dto.toDomain())
    }


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

    private fun debug(dto: MovieDto) {
        Log.d("Movie Service", dto.toString())
    }

}
