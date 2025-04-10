package pl.edu.pja.s27773.filmoteka.view.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import pl.edu.pja.s27773.filmoteka.R
import pl.edu.pja.s27773.filmoteka.model.dto.MovieDto
import pl.edu.pja.s27773.filmoteka.service.MovieService

class PreviewMovieActivity : AppCompatActivity() {

    private lateinit var movie: MovieDto

    private lateinit var title: TextView
    private lateinit var date: TextView
    private lateinit var status: TextView
    private lateinit var category: TextView
    private lateinit var rating: TextView
    private lateinit var poster: ImageView
    private lateinit var posterTitle: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_preview_movie)

        applySystemInsets()

        val movieId = intent.getLongExtra("movie_id", -1L)
        if (movieId == -1L) {
            finish()
            return
        }
        movie = MovieService.getById(movieId.toInt()) ?: run {
            finish()
            return
        }

        initViews()
    }

    private fun initViews() {
        title = findViewById(R.id.movie_title)
        date = findViewById(R.id.movie_date)
        status = findViewById(R.id.movie_status)
        category = findViewById(R.id.movie_category)
        rating = findViewById(R.id.movie_rating)
        poster = findViewById(R.id.movie_poster)
        posterTitle = findViewById(R.id.movie_poster_title)
        title.text = movie.title
        date.text = movie.releaseDate.toString()
        status.text = movie.status.toString()
        category.text = movie.category.toString()
        rating.text = movie.rating.toString()
        posterTitle.text = movie.title
        posterTitle.visibility = if (movie.posterUri != null) View.GONE else View.VISIBLE
        if (movie.posterUri != null) {
            poster.setImageURI(movie.posterUri)
            posterTitle.visibility = View.GONE
        } else {
            poster.setImageResource(R.drawable.image_placeholder)
            posterTitle.visibility = View.VISIBLE
        }

        findViewById<ImageView>(R.id.back_button).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

    }

    private fun applySystemInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
