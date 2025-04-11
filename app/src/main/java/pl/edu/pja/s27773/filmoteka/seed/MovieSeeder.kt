package pl.edu.pja.s27773.filmoteka.seed

import android.net.Uri
import pl.edu.pja.s27773.filmoteka.model.*
import pl.edu.pja.s27773.filmoteka.repository.MovieRepository
import java.time.LocalDate

object MovieSeeder {

    fun seed() {
        MovieRepository.clear()

        var idCounter = 1;
        fun nextSeedId() = Id.of(idCounter++)
        val packageName = "pl.edu.pja.s27773.filmoteka"

        try {
            val movies = listOf(
                Movie(
                    nextSeedId(), Title.of("Inception"),
                    ReleaseDate.of(LocalDate.of(2010, 7, 16)),
                    Category.MOVIE,
                    Status.WATCHED,
                    Rating.of(7),
                    Uri.parse("android.resource://$packageName/drawable/inception")
                ),
                Movie(
                    nextSeedId(), Title.of("The Godfather"),
                    ReleaseDate.of(LocalDate.of(1972, 3, 24)),
                    Category.MOVIE,
                    Status.WATCHED,
                    Rating.of(10),
                    Uri.parse("android.resource://$packageName/drawable/god_father")
                ),
                Movie(
                    nextSeedId(), Title.of("The Dark Knight"),
                    ReleaseDate.of(LocalDate.of(2008, 7, 18)),
                    Category.MOVIE,
                    Status.WATCHED,
                    Rating.of(7),
                    Uri.parse("android.resource://$packageName/drawable/dark_knight")
                ),

                Movie(
                    nextSeedId(),
                    Title.of("Planet Earth"),
                    ReleaseDate.of(LocalDate.of(2006, 3, 5)),
                    Category.DOCUMENTARY,
                    Status.NOT_WATCHED,
                    null,
                    Uri.parse("android.resource://$packageName/drawable/planet_earth")
                ),
                Movie(
                    nextSeedId(),
                    Title.of("Breaking Bad"),
                    ReleaseDate.of(LocalDate.of(2008, 1, 20)),
                    Category.SERIES,
                    Status.WATCHED,
                    Rating.of(8),
                    Uri.parse("android.resource://$packageName/drawable/breaking_bad")
                ),
                Movie(
                    nextSeedId(),
                    Title.of("Game Of Thrones"),
                    ReleaseDate.of(LocalDate.of(2011, 4, 17)),
                    Category.SERIES,
                    Status.NOT_WATCHED,
                    null,
                    Uri.parse("android.resource://$packageName/drawable/game_of_thrones")
                ),
                Movie(
                    nextSeedId(),
                    Title.of("The Matrix"),
                    ReleaseDate.of(LocalDate.of(1999, 3, 31)),
                    Category.MOVIE,
                    Status.WATCHED,
                    Rating.of(7),
                    Uri.parse("android.resource://$packageName/drawable/matrix")
                ),

                Movie(
                    nextSeedId(),
                    Title.of("Interstellar"),
                    ReleaseDate.of(LocalDate.of(2014, 11, 7)),
                    Category.MOVIE,
                    Status.WATCHED,
                    Rating.of(7),
                    Uri.parse("android.resource://$packageName/drawable/interstellar")
                ),
                Movie(
                    nextSeedId(),
                    Title.of("Chernobyl"),
                    ReleaseDate.of(LocalDate.of(2019, 5, 6)),
                    Category.SERIES,
                    Status.WATCHED,
                    Rating.of(8),
                    Uri.parse("android.resource://$packageName/drawable/chernobyl")
                ),
                Movie(
                    nextSeedId(),
                    Title.of("Schindler's List"),
                    ReleaseDate.of(LocalDate.of(1993, 12, 15)),
                    Category.MOVIE,
                    Status.WATCHED,
                    Rating.of(10),
                    Uri.parse("android.resource://$packageName/drawable/schindler")
                ),
                Movie(
                    nextSeedId(),
                    Title.of("Snow White"),
                    ReleaseDate.of(
                        LocalDate.of(2025, 4, 2)
                    ),
                    Category.MOVIE,
                    Status.WATCHED,
                    Rating.of(1),
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