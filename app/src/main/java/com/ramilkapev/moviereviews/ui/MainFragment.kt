package com.ramilkapev.moviereviews.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ramilkapev.moviereviews.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    @Inject
    lateinit var moviesAdapter: MoviesAdapter

    private val mainViewModel: MainViewModel by viewModels()
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.rvMovies) {
            val orientation = RecyclerView.VERTICAL
            val screenOrientation = resources.configuration.orientation
            binding.rvMovies.adapter =
                moviesAdapter.withLoadStateFooter(MoviesLoaderStateAdapter(moviesAdapter))
            if (screenOrientation == Configuration.ORIENTATION_PORTRAIT) {
                layoutManager = LinearLayoutManager(context, orientation, false)
                addItemDecoration(DividerItemDecoration(context, orientation))
            } else if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                layoutManager = GridLayoutManager(context, 2)
            }
        }

        mainViewModel.getMovies()

        lifecycleScope.launch {
            mainViewModel.moviesFlow?.collectLatest(moviesAdapter::submitData)
        }

        binding.btnRefresh.setOnClickListener {
            moviesAdapter.retry()
        }

        moviesAdapter.addLoadStateListener { state: CombinedLoadStates ->
            val refreshState = state.refresh
            binding.rvMovies.isVisible = refreshState != LoadState.Loading
            binding.progressBar.isVisible = refreshState == LoadState.Loading

            if (refreshState is LoadState.Error) {
                binding.btnRefresh.isVisible = true
                binding.txvError.isVisible = true
            } else {
                binding.btnRefresh.isVisible = false
                binding.txvError.isVisible = false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}