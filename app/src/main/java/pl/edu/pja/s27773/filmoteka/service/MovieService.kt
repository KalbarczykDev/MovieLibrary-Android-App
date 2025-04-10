package pl.edu.pja.s27773.filmoteka.service

import android.util.Log
import pl.edu.pja.s27773.filmoteka.error.MovieCrudError
import pl.edu.pja.s27773.filmoteka.model.dto.MovieDto
import pl.edu.pja.s27773.filmoteka.repository.MovieRepository
import pl.edu.pja.s27773.filmoteka.mapper.toDomain
import pl.edu.pja.s27773.filmoteka.mapper.toDto
import pl.edu.pja.s27773.filmoteka.model.Id

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
        MovieRepository.add(dto.toDomain())
    }

    fun delete(dto: MovieDto){
         MovieRepository.delete(dto.toDomain())
    }

    fun update(dto: MovieDto) {
         MovieRepository.update(dto.toDomain())
    }

}
