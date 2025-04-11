package pl.edu.pja.s27773.filmoteka.service

import pl.edu.pja.s27773.filmoteka.error.AppErrorException
import pl.edu.pja.s27773.filmoteka.error.MovieCrudError
import pl.edu.pja.s27773.filmoteka.model.dto.MovieDto
import pl.edu.pja.s27773.filmoteka.repository.MovieRepository
import pl.edu.pja.s27773.filmoteka.mapper.toDomain
import pl.edu.pja.s27773.filmoteka.mapper.toDto
import pl.edu.pja.s27773.filmoteka.model.Category
import pl.edu.pja.s27773.filmoteka.model.Id
import pl.edu.pja.s27773.filmoteka.model.Status

object MovieService {
    fun getAll(): List<MovieDto> {
        return MovieRepository.getAll()
            .sortedBy { it.releaseDate.toString() }
            .map { it.toDto() }
    }

    fun getById(id: Int): MovieDto? {
        if (id < 0) return null
        return MovieRepository.getById(Id.of(id))?.toDto()
    }

    fun add(dto: MovieDto) {

        if (dto.category == Category.NONE) {
            throw AppErrorException(MovieCrudError.CategoryNotSelected)
        }

        if (dto.status == Status.NONE) {
            throw AppErrorException(MovieCrudError.StatusNotSelected)
        }

        MovieRepository.add(dto.toDomain())
    }

    fun delete(dto: MovieDto) {
        MovieRepository.delete(dto.toDomain())
    }

    fun update(dto: MovieDto) {
        if (dto.category == Category.NONE) {
            throw AppErrorException(MovieCrudError.CategoryNotSelected)
        }

        if (dto.status == Status.NONE) {
            throw AppErrorException(MovieCrudError.StatusNotSelected)
        }

        MovieRepository.update(dto.toDomain())
    }

}
