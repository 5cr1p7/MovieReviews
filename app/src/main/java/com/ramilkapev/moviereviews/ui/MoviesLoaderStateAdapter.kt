package com.ramilkapev.moviereviews.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ramilkapev.moviereviews.databinding.ItemErrorBinding
import com.ramilkapev.moviereviews.databinding.ItemProgressBinding

class MoviesLoaderStateAdapter(
    private val adapter: MoviesAdapter
) : LoadStateAdapter<MoviesLoaderStateAdapter.ItemViewHolder>() {

    override fun getStateViewType(loadState: LoadState) = when (loadState) {
        is LoadState.NotLoading -> error("Not supported")
        LoadState.Loading -> PROGRESS
        is LoadState.Error -> ERROR
    }

    override fun onBindViewHolder(holder: ItemViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ItemViewHolder {
        return when (loadState) {
            LoadState.Loading -> ProgressViewHolder(LayoutInflater.from(parent.context), parent)
            is LoadState.Error -> ErrorViewHolder(LayoutInflater.from(parent.context), parent) {
                adapter.retry()
            }
            is LoadState.NotLoading -> error("Not supported")
        }
    }

    private companion object {

        private const val ERROR = 1
        private const val PROGRESS = 0
    }

    abstract class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        abstract fun bind(loadState: LoadState)
    }

    class ProgressViewHolder internal constructor(
        binding: ItemProgressBinding
    ) : ItemViewHolder(binding.root) {

        override fun bind(loadState: LoadState) {
        }

        companion object {

            operator fun invoke(
                layoutInflater: LayoutInflater,
                parent: ViewGroup? = null,
                attachToRoot: Boolean = false
            ): ProgressViewHolder {
                return ProgressViewHolder(
                    ItemProgressBinding.inflate(
                        layoutInflater,
                        parent,
                        attachToRoot
                    )
                )
            }
        }
    }

    class ErrorViewHolder internal constructor(
        private val binding: ItemErrorBinding,
        private val retryCallback: () -> Unit
    ) : ItemViewHolder(binding.root) {

        override fun bind(loadState: LoadState) {
            require(loadState is LoadState.Error)
            binding.errorMessage.text = loadState.error.localizedMessage
            binding.btnRetry.setOnClickListener {
                retryCallback()
            }
        }

        companion object {

            operator fun invoke(
                layoutInflater: LayoutInflater,
                parent: ViewGroup? = null,
                attachToRoot: Boolean = false,
                retryCallback: () -> Unit
            ): ErrorViewHolder {
                return ErrorViewHolder(
                    ItemErrorBinding.inflate(
                        layoutInflater,
                        parent,
                        attachToRoot
                    )
                ) {
                    retryCallback()
                }
            }
        }
    }
}