package pl.edu.pja.s27773.filmoteka.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import pl.edu.pja.s27773.filmoteka.R
import pl.edu.pja.s27773.filmoteka.model.Status
import pl.edu.pja.s27773.filmoteka.model.dto.MovieDto

class MovieAdapter(private var movies: List<MovieDto>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.movie_title)
        val date: TextView = view.findViewById(R.id.movie_date)
        val status: TextView = view.findViewById(R.id.movie_status)
        val category: TextView = view.findViewById(R.id.movie_category)
        val rating: TextView = view.findViewById(R.id.movie_rating)
        val poster: ImageView = view.findViewById(R.id.movie_poster)
        val posterTitle: TextView = view.findViewById(R.id.movie_poster_title)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.title.text = movie.title
        holder.date.text = movie.releaseDate.toString()
        holder.category.text = holder.itemView.context.getString(movie.category.stringResId)
        holder.posterTitle.text = movie.title


        if (movie.posterUri != null) {
            holder.poster.setImageURI(movie.posterUri)
        } else {
            holder.poster.setImageResource(R.drawable.image_placeholder)
        }


        holder.status.text = holder.itemView.context.getString(movie.status.stringResId)
        val statusColorRes = if (movie.status == Status.WATCHED) {
            R.color.status_watched
        } else {
            R.color.status_unwatched
        }

        if (movie.status == Status.NOT_WATCHED) {
            holder.status.visibility = View.GONE
        } else {
            holder.status.visibility = View.VISIBLE
        }

        if (movie.status == Status.WATCHED && movie.rating != null) {
            holder.rating.visibility = View.VISIBLE
            holder.rating.text = "${movie.rating}/10"
        } else {
            holder.rating.visibility = View.GONE
        }

        holder.status.setBackgroundResource(R.drawable.status_badge)
        holder.status.backgroundTintList = ContextCompat.getColorStateList(holder.itemView.context, statusColorRes)


    }

    override fun getItemCount(): Int = movies.size

    fun updateData(newMovies: List<MovieDto>) {
        movies = newMovies
        notifyDataSetChanged()
    }
}