package pl.edu.pja.s27773.filmoteka.seed

import android.net.Uri
import pl.edu.pja.s27773.filmoteka.model.*
import pl.edu.pja.s27773.filmoteka.repository.MovieRepository
import java.time.LocalDate

object MovieSeeder {

    fun seed() {
        MovieRepository.clear()

        var idCounter = 1
        fun nextSeedId() = MovieId.of(idCounter++)
        val packageName = "pl.edu.pja.s27773.filmoteka"

        try {
            val movies = listOf(
                Movie(
                    nextSeedId(), MovieTitle.of("Inception"),
                    MovieReleaseDate.of(LocalDate.of(2010, 7, 16)),
                    MovieCategory.MOVIE,
                    MovieStatus.WATCHED,
                    MovieRating.of(3f),
                    Uri.parse("android.resource://$packageName/drawable/inception")
                ),
                Movie(
                    nextSeedId(), MovieTitle.of("The Godfather"),
                    MovieReleaseDate.of(LocalDate.of(1972, 3, 24)),
                    MovieCategory.MOVIE,
                    MovieStatus.WATCHED,
                    MovieRating.of(5f),
                    Uri.parse("android.resource://$packageName/drawable/god_father")
                ),
                Movie(
                    nextSeedId(), MovieTitle.of("The Dark Knight"),
                    MovieReleaseDate.of(LocalDate.of(2008, 7, 18)),
                    MovieCategory.MOVIE,
                    MovieStatus.WATCHED,
                    MovieRating.of(4f),
                    Uri.parse("android.resource://$packageName/drawable/dark_knight")
                ),

                Movie(
                    nextSeedId(),
                    MovieTitle.of("Planet Earth"),
                    MovieReleaseDate.of(LocalDate.of(2006, 3, 5)),
                    MovieCategory.DOCUMENTARY,
                    MovieStatus.NOT_WATCHED,
                    null,
                    Uri.parse("android.resource://$packageName/drawable/planet_earth")
                ),
                Movie(
                    nextSeedId(),
                    MovieTitle.of("Breaking Bad"),
                    MovieReleaseDate.of(LocalDate.of(2008, 1, 20)),
                    MovieCategory.SERIES,
                    MovieStatus.WATCHED,
                    MovieRating.of(4.5f),
                    Uri.parse("android.resource://$packageName/drawable/breaking_bad")
                ),
                Movie(
                    nextSeedId(),
                    MovieTitle.of("Game Of Thrones"),
                    MovieReleaseDate.of(LocalDate.of(2011, 4, 17)),
                    MovieCategory.SERIES,
                    MovieStatus.NOT_WATCHED,
                    null,
                    Uri.parse("android.resource://$packageName/drawable/game_of_thrones")
                ),
                Movie(
                    nextSeedId(),
                    MovieTitle.of("The Matrix"),
                    MovieReleaseDate.of(LocalDate.of(1999, 3, 31)),
                    MovieCategory.MOVIE,
                    MovieStatus.WATCHED,
                    MovieRating.of(4f),
                    Uri.parse("android.resource://$packageName/drawable/matrix")
                ),

                Movie(
                    nextSeedId(),
                    MovieTitle.of("Interstellar"),
                    MovieReleaseDate.of(LocalDate.of(2014, 11, 7)),
                    MovieCategory.MOVIE,
                    MovieStatus.WATCHED,
                    MovieRating.of(3.5f),
                    Uri.parse("android.resource://$packageName/drawable/interstellar")
                ),
                Movie(
                    nextSeedId(),
                    MovieTitle.of("Chernobyl"),
                    MovieReleaseDate.of(LocalDate.of(2019, 5, 6)),
                    MovieCategory.SERIES,
                    MovieStatus.WATCHED,
                    MovieRating.of(3f),
                    Uri.parse("android.resource://$packageName/drawable/chernobyl")
                ),
                Movie(
                    nextSeedId(),
                    MovieTitle.of("Schindler's List"),
                    MovieReleaseDate.of(LocalDate.of(1993, 12, 15)),
                    MovieCategory.MOVIE,
                    MovieStatus.WATCHED,
                    MovieRating.of(4f),
                    Uri.parse("android.resource://$packageName/drawable/schindler")
                ),
                Movie(
                    nextSeedId(),
                    MovieTitle.of("Snow White"),
                    MovieReleaseDate.of(
                        LocalDate.of(2025, 4, 2)
                    ),
                    MovieCategory.MOVIE,
                    MovieStatus.WATCHED,
                    MovieRating.of(1f),
                    Uri.parse("android.resource://$packageName/drawable/snow")
                ),
            )

            for (movie in movies) {
                MovieRepository.add(movie)
            }

        } catch (e: Exception) {
            println("Error seeding movies: ${e.message}")
        }


    }

}