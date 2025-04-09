package pl.edu.pja.s27773.filmoteka

import android.app.Application
import pl.edu.pja.s27773.filmoteka.repository.MovieRepository
import pl.edu.pja.s27773.filmoteka.seed.MovieSeeder

class FilmotekaApp : Application() {
    override fun onCreate() {
        super.onCreate()

        //Load Sample data
        MovieSeeder.seed()
    }
}