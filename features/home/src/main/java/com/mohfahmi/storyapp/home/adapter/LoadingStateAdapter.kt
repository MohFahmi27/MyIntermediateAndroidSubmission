package com.mohfahmi.storyapp.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mohfahmi.storyapp.core.utils.visible
import com.mohfahmi.storyapp.home.databinding.ItemLoadingBinding

class LoadingStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<LoadingStateAdapter.LoadingStateViewHolder>() {

    inner class LoadingStateViewHolder(private val binding: ItemLoadingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.btnTryAgain.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            with(binding) {
                when (loadState) {
                    is LoadState.Loading -> {
                        vlMain.veil()
                    }
                    is LoadState.NotLoading -> {
                        vlMain.unVeil()
                    }
                    is LoadState.Error -> {
                        tvErrorMessage.visible()
                        btnTryAgain.visible()
                        tvErrorMessage.text = loadState.error.localizedMessage
                    }
                }
            }
        }
    }

    override fun onBindViewHolder(holder: LoadingStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadingStateViewHolder {
        val binding = ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadingStateViewHolder(binding)
    }
}