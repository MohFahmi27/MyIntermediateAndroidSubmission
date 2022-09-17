package com.mohfahmi.storyapp.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mohfahmi.storyapp.core.R.drawable.img_error_image
import com.mohfahmi.storyapp.core.R.drawable.img_photo_loading
import com.mohfahmi.storyapp.core.domain.models.Story
import com.mohfahmi.storyapp.home.databinding.ItemsStoryBinding

class StoriesAdapter(
    private val storyData: ArrayList<Story>,
    private val clickListener: (Story, ImageView, TextView, TextView) -> Unit
) :
    RecyclerView.Adapter<StoriesAdapter.StoriesAdapterViewHolder>() {


    inner class StoriesAdapterViewHolder(private val binding: ItemsStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(story: Story) {
            with(binding) {
                tvStoryName.text = story.name.replaceFirstChar {
                    it.uppercase()
                }
                tvStoryDescription.text = story.description
                imgStory.load(story.photoUrl) {
                    error(img_error_image)
                    placeholder(img_photo_loading)
                }


                root.setOnClickListener {
                    clickListener(story, imgStory, tvStoryName, tvStoryDescription)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoriesAdapterViewHolder {
        return StoriesAdapterViewHolder(
            ItemsStoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StoriesAdapterViewHolder, position: Int) {
        holder.bind(storyData[position])
    }

    override fun getItemCount(): Int = storyData.size

}