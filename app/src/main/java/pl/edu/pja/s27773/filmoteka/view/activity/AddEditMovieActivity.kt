package pl.edu.pja.s27773.filmoteka.view.activity

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import pl.edu.pja.s27773.filmoteka.R
import pl.edu.pja.s27773.filmoteka.error.AppErrorException
import pl.edu.pja.s27773.filmoteka.model.*
import pl.edu.pja.s27773.filmoteka.model.dto.MovieDto
import pl.edu.pja.s27773.filmoteka.service.MovieService
import java.time.LocalDate

class AddEditMovieActivity : AppCompatActivity() {

    private lateinit var categorySpinner: Spinner
    private lateinit var statusSpinner: Spinner
    private lateinit var titleInput: TextInputEditText
    private lateinit var saveButton: MaterialButton
    private lateinit var releaseDateText: TextView
    private lateinit var ratingBar: RatingBar
    private lateinit var posterImage: ImageView
    private lateinit var backButton: ImageView

    private lateinit var categoryMap: Map<String, Category>
    private lateinit var statusMap: Map<String, Status>

    private var isEditMode = false
    private var movieId: Int? = null
    private var currentMovie: MovieDto? = null
    private var selectedPosterUri: Uri? = null
    private var selectedReleaseDate: LocalDate? = null

    private val REQUEST_IMAGE_PICK = 1001

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
        ratingBar = findViewById(R.id.movie_rating)
        posterImage = findViewById(R.id.poster_image)

        findViewById<MaterialToolbar>(R.id.top).setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        saveButton.setOnClickListener {
            handleSave()
        }

        posterImage.setOnClickListener {
            openGallery()
        }
    }

    private fun setupSpinners() {
        categoryMap = mapOf(
            getString(R.string.category_none) to Category.NONE,
            getString(R.string.category_movie) to Category.MOVIE,
            getString(R.string.category_series) to Category.SERIES,
            getString(R.string.category_documentary) to Category.DOCUMENTARY
        )

        statusMap = mapOf(
            getString(R.string.status_none) to Status.NONE,
            getString(R.string.status_watched) to Status.WATCHED,
            getString(R.string.status_not_watched) to Status.NOT_WATCHED
        )

        categorySpinner.adapter = ArrayAdapter(
            this, R.layout.spinner_item, categoryMap.keys.toList()
        ).apply {
            setDropDownViewResource(R.layout.spinner_item)
        }

        statusSpinner.adapter = ArrayAdapter(
            this, R.layout.spinner_item, statusMap.keys.toList()
        ).apply {
            setDropDownViewResource(R.layout.spinner_item)
        }
    }

    private fun fillFormWithMovie(movie: MovieDto) {
        titleInput.setText(movie.title)
        statusSpinner.setSelection(statusMap.values.indexOf(movie.status))
        categorySpinner.setSelection(categoryMap.values.indexOf(movie.category))
        releaseDateText.text = movie.releaseDate.toString()
        selectedReleaseDate = movie.releaseDate
        ratingBar.rating = (movie.rating ?: 0).toFloat()
        selectedPosterUri = movie.posterUri
        posterImage.setImageURI(movie.posterUri)
    }

    private fun setupDatePicker() {
        val today = LocalDate.now()
        var selectedDate = today

        releaseDateText.setOnClickListener {
            val datePicker = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
                    releaseDateText.text = selectedDate.toString()
                    selectedReleaseDate = selectedDate
                },
                today.year,
                today.monthValue - 1,
                today.dayOfMonth
            )

            val calendar = Calendar.getInstance().apply {
                add(Calendar.YEAR, 2)
            }
            datePicker.datePicker.maxDate = calendar.timeInMillis

            datePicker.show()
        }
    }

    @SuppressLint("DiscouragedApi")
    private fun handleSave() {
        val title = titleInput.text.toString()
        val selectedStatus = statusMap[statusSpinner.selectedItem.toString()] ?: Status.NONE
        val selectedCategory = categoryMap[categorySpinner.selectedItem.toString()] ?: Category.NONE
        val releaseDate = selectedReleaseDate ?: LocalDate.now()
        val rating = if (selectedStatus == Status.WATCHED) ratingBar.rating.toInt() else null

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
            Log.e("AddEditMovieActivity", e.message.toString())
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_IMAGE_PICK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK) {
            selectedPosterUri = data?.data
            posterImage.setImageURI(selectedPosterUri)
        }
    }
}
