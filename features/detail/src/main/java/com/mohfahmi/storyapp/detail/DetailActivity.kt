package com.mohfahmi.storyapp.detail

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.viewbinding.library.activity.viewBinding
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.mohfahmi.storyapp.core.R
import com.mohfahmi.storyapp.core.domain.models.Story
import com.mohfahmi.storyapp.core.utils.NavigationHelper
import com.mohfahmi.storyapp.core.utils.capitalize
import com.mohfahmi.storyapp.detail.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private val binding: ActivityDetailBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
    }

    private fun initViews() {
        with(binding) {
            val storyIntent =
                intent.getParcelableExtra<Story>(NavigationHelper.DETAIL_EXTRA) as Story

            imgStory.load(storyIntent.photoUrl) {
                error(R.drawable.img_error_image)
                placeholder(R.drawable.img_photo_loading)
            }
            tvStoryName.text = storyIntent.name.capitalize()
            val spannable = SpannableString(storyIntent.description.capitalize())
            spannable.setSpan(
                RelativeSizeSpan(2.5f),
                0,
                1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            tvStoryDescription.text = spannable

            btnBack.setOnClickListener {
                onBackPressed()
            }
        }
    }


}