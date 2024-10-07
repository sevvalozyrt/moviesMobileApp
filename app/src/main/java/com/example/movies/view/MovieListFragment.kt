package com.example.movies.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.movies.adapter.MovieRecyclerAdapter
import com.example.movies.databinding.FragmentMovieListBinding
import com.example.movies.service.MovieAPI
import com.example.movies.viewmodel.MovieListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MovieListFragment : Fragment() {

    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : MovieListViewModel
    private val movieRecyclerAdapter = MovieRecyclerAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[MovieListViewModel::class.java]
        viewModel.refreshData()

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = movieRecyclerAdapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.recyclerView.visibility = View.GONE
            binding.movieErrorMessage.visibility = View.GONE
            binding.movieLoading.visibility = View.VISIBLE
            viewModel.refreshDataFromInternet()
            binding.swipeRefreshLayout.isRefreshing = false
        }
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.movies.observe(viewLifecycleOwner) {
            binding.recyclerView.visibility = View.VISIBLE
            movieRecyclerAdapter.updateMovieList(it)
        }
        viewModel.movieErrorMessage.observe(viewLifecycleOwner) {
            if(it) {
                binding.movieErrorMessage.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            } else {
                binding.movieErrorMessage.visibility = View.GONE
            }
        }
        viewModel.movieLoading.observe(viewLifecycleOwner) {
            if(it) {
                binding.movieErrorMessage.visibility = View.GONE
                binding.recyclerView.visibility = View.GONE
                binding.movieLoading.visibility = View.VISIBLE
            } else {
                binding.movieLoading.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}