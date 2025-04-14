package pl.edu.pja.s27773.filmoteka.view.activity


import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import pl.edu.pja.s27773.filmoteka.R
import pl.edu.pja.s27773.filmoteka.model.dto.MovieDto
import pl.edu.pja.s27773.filmoteka.service.MovieService

class MovieDetails : AppCompatActivity() {

    private lateinit var categoryTextView: TextView
    private lateinit var statusTextView: TextView
    private lateinit var titleInput: TextView
    private lateinit var releaseDateText: TextView
    private lateinit var posterImage: ImageView
    private lateinit var ratingBar: AppCompatRatingBar


    private var movieId: Int? = null
    private var currentMovie: MovieDto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_movie_details)
        applySystemInsets()
        initViews()

        movieId = intent.getLongExtra("movie_id", -1).toInt()
        if (movieId != -1) {
            currentMovie = MovieService.getById(movieId!!)
            currentMovie?.let { fillFormWithMovie(it) }
        }
    }

    private fun applySystemInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initViews() {
        categoryTextView = findViewById(R.id.category_value)
        statusTextView = findViewById(R.id.status_value)
        titleInput = findViewById(R.id.title_input)
        releaseDateText = findViewById(R.id.release_date_text)
        posterImage = findViewById(R.id.poster_image)
        ratingBar = findViewById(R.id.rating_bar)

        findViewById<ImageButton>(R.id.back_button).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        posterImage.isClickable = false
        posterImage.isFocusable = false

    }


    private fun fillFormWithMovie(movie: MovieDto) {
        titleInput.text = movie.title
        statusTextView.text = movie.status.name
        categoryTextView.text = movie.category.name
        ratingBar.rating = movie.rating ?: 0f
        releaseDateText.text = movie.releaseDate.toString()
        posterImage.setImageURI(movie.posterUri)
    }
}