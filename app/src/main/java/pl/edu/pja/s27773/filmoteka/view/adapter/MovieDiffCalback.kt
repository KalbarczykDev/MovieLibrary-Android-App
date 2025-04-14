package pl.edu.pja.s27773.filmoteka.view.adapter

import androidx.recyclerview.widget.DiffUtil
import pl.edu.pja.s27773.filmoteka.model.dto.MovieDto

class MovieDiffCallback(
    private val oldList: List<MovieDto>,
    private val newList: List<MovieDto>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
