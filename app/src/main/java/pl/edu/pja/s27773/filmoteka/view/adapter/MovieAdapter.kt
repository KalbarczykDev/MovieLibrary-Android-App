package pl.edu.pja.s27773.filmoteka.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.edu.pja.s27773.filmoteka.R
import pl.edu.pja.s27773.filmoteka.model.dto.MovieDto

class MovieAdapter(private var movies: List<MovieDto>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.movie_title)
        val date: TextView = view.findViewById(R.id.movie_date)
        val status: TextView = view.findViewById(R.id.movie_status)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.title.text = movie.title
        holder.date.text = movie.releaseDate.toString()
        holder.status.text = movie.status.name
    }

    override fun getItemCount(): Int = movies.size

    fun updateData(newMovies: List<MovieDto>) {
        movies = newMovies
        notifyDataSetChanged()
    }
}