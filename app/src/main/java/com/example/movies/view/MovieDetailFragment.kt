package com.example.movies.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.movies.databinding.FragmentMovieDetailBinding
import com.example.movies.util.gorselIndir
import com.example.movies.util.placeHolderYap
import com.example.movies.viewmodel.MovieDetailViewModel

class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : MovieDetailViewModel
    var movieId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[MovieDetailViewModel::class.java]
        arguments?.let {
            movieId = MovieDetailFragmentArgs.fromBundle(it).movieId
        }
        viewModel.roomVerisiniAl(movieId)
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.movieLiveData.observe(viewLifecycleOwner) { movie ->
            binding.movieName.text = movie.movie
            binding.movieRating.text = movie.rating.toString()
            binding.movieImdbUrl.text = movie.imdb_url
            binding.movieImage.gorselIndir(movie.image, placeHolderYap(requireContext()))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}