package pl.edu.pja.s27773.filmoteka.view.activity

import android.app.DatePickerDialog
import android.content.pm.PackageManager
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import pl.edu.pja.s27773.filmoteka.R
import pl.edu.pja.s27773.filmoteka.error.AppErrorException
import pl.edu.pja.s27773.filmoteka.model.*
import pl.edu.pja.s27773.filmoteka.model.dto.MovieDto
import pl.edu.pja.s27773.filmoteka.service.MovieService
import java.time.LocalDate
import android.Manifest
import android.content.Intent
import android.view.View


class AddEditMovieActivity : AppCompatActivity() {

    private lateinit var categorySpinner: Spinner
    private lateinit var statusSpinner: Spinner
    private lateinit var titleInput: EditText
    private lateinit var saveButton: FloatingActionButton
    private lateinit var releaseDateText: TextView
    private lateinit var posterImage: ImageView
    private lateinit var ratingBar: AppCompatRatingBar

    private lateinit var categoryMap: Map<String, MovieCategory>
    private lateinit var statusMap: Map<String, MovieStatus>

    private var isEditMode = false
    private var movieId: Int? = null
    private var currentMovie: MovieDto? = null
    private var selectedPosterUri: Uri? = null
    private var selectedReleaseDate: LocalDate? = null
    private var isRestoringForm = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_edit_movie)
        applySystemInsets()
        initViews()
        setupSpinners()
        setupDatePicker()

        movieId = intent.getLongExtra("movie_id", -1).toInt()
        if (movieId != -1) {
            isEditMode = true
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
        categorySpinner = findViewById(R.id.category_spinner)
        statusSpinner = findViewById(R.id.status_spinner)
        titleInput = findViewById(R.id.title_input)
        saveButton = findViewById(R.id.save_button)
        releaseDateText = findViewById(R.id.release_date_text)
        posterImage = findViewById(R.id.poster_image)
        ratingBar = findViewById(R.id.rating_bar)

        findViewById<ImageButton>(R.id.back_button).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        saveButton.setOnClickListener { handleSave() }

        posterImage.setOnClickListener {
            openGallery()
        }

    }


    private fun setupSpinners() {
        categoryMap = mapOf(
            getString(R.string.category_movie) to MovieCategory.MOVIE,
            getString(R.string.category_series) to MovieCategory.SERIES,
            getString(R.string.category_documentary) to MovieCategory.DOCUMENTARY
        )

        statusMap = mapOf(
            getString(R.string.status_watched) to MovieStatus.WATCHED,
            getString(R.string.status_not_watched) to MovieStatus.NOT_WATCHED
        )

        categorySpinner.adapter = ArrayAdapter(this, R.layout.spinner_item, categoryMap.keys.toList())
        statusSpinner.adapter = ArrayAdapter(this, R.layout.spinner_item, statusMap.keys.toList())

        statusSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val status = statusMap[statusSpinner.selectedItem.toString()]
                ratingBar.isEnabled = (status == MovieStatus.WATCHED)
                if (status != MovieStatus.WATCHED) {
                    ratingBar.rating = 0f
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }


    private fun setupDatePicker() {
        val today = LocalDate.now()
        releaseDateText.setOnClickListener {
            val datePicker = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    val selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
                    releaseDateText.text = selectedDate.toString()
                    selectedReleaseDate = selectedDate
                },
                today.year,
                today.monthValue - 1,
                today.dayOfMonth
            )
            val calendar = Calendar.getInstance().apply { add(Calendar.YEAR, 2) }
            datePicker.datePicker.maxDate = calendar.timeInMillis
            datePicker.show()
        }
    }

    private fun fillFormWithMovie(movie: MovieDto) {
        isRestoringForm = true
        titleInput.setText(movie.title)
        statusSpinner.setSelection(statusMap.values.indexOf(movie.status))
        categorySpinner.setSelection(categoryMap.values.indexOf(movie.category))
        ratingBar.rating = movie.rating ?: 0f
        releaseDateText.text = movie.releaseDate.toString()
        selectedReleaseDate = movie.releaseDate
        selectedPosterUri = movie.posterUri
        posterImage.setImageURI(movie.posterUri)
        isRestoringForm = false
    }

    private fun openGallery() {
        imagePickerLauncher.launch(arrayOf("image/*"))
    }


    private val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
            uri?.let {
                try {
                    contentResolver.takePersistableUriPermission(
                        it,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                } catch (e: SecurityException) {
                    Log.e("ImagePicker", "Failed to persist permission", e)
                }

                selectedPosterUri = it
                posterImage.setImageURI(it)
            }
        }


    private fun handleSave() {
        val title = titleInput.text.toString()
        val selectedStatus = statusMap[statusSpinner.selectedItem.toString()] ?: MovieStatus.NONE
        val selectedCategory = categoryMap[categorySpinner.selectedItem.toString()] ?: MovieCategory.NONE
        val releaseDate = selectedReleaseDate ?: LocalDate.now()
        val rating = if (selectedStatus == MovieStatus.WATCHED) {
            ratingBar.rating
        } else {
            null
        }


        Log.d("AddEditMovieActivity", "Selected rating: $rating")


        val dto = MovieDto(
            id = if (isEditMode) movieId else null,
            title = title,
            releaseDate = releaseDate,
            category = selectedCategory,
            status = selectedStatus,
            rating = rating,
            posterUri = selectedPosterUri
        )

        try {
            if (isEditMode) {
                MovieService.update(dto)
                Toast.makeText(this, getString(R.string.movie_updated), Toast.LENGTH_SHORT).show()
            } else {
                MovieService.add(dto)
                Toast.makeText(this, getString(R.string.movie_added), Toast.LENGTH_SHORT).show()
            }
            setResult(RESULT_OK)
            finish()
        } catch (e: AppErrorException) {
            val errorKey = e.message
            val errorMsg = getString(resources.getIdentifier(errorKey, "string", packageName))
            Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Log.e("AddEditMovieActivity", "Unexpected error: ${e.message}", e)
        }
    }

}
