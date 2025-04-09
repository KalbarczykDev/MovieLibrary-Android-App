package pl.edu.pja.s27773.filmoteka

import android.app.Application
import pl.edu.pja.s27773.filmoteka.repository.MovieRepository

class FilmotekaApp : Application() {
    override fun onCreate() {
        super.onCreate()

        //Load Sample data
        MovieRepository.loadSampleData()
    }
}