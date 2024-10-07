package com.example.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.databinding.MovieRecyclerRowBinding
import com.example.movies.model.Movie
import com.example.movies.util.gorselIndir
import com.example.movies.util.placeHolderYap
import com.example.movies.view.MovieListFragmentDirections

class MovieRecyclerAdapter(val movieList : ArrayList<Movie>) : RecyclerView.Adapter<MovieRecyclerAdapter.MovieViewHolder>() {

    class MovieViewHolder(val binding : MovieRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun updateMovieList(newMovieList : List<Movie>) {
        movieList.clear()
        movieList.addAll(newMovieList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.binding.isim.text = movieList[position].movie
        holder.binding.puan.text = movieList[position].rating.toString()

        holder.itemView.setOnClickListener {
            val action = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(movieList[position].id)
            Navigation.findNavController(it).navigate(action)
        }
        holder.binding.imageView.gorselIndir(movieList[position].image, placeHolderYap(holder.itemView.context))
    }
}