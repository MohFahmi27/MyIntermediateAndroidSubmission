package com.mohfahmi.storyapp.home

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.viewbinding.library.activity.viewBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.LinearLayoutManager
import com.mohfahmi.storyapp.core.domain.models.Story
import com.mohfahmi.storyapp.core.utils.*
import com.mohfahmi.storyapp.core.utils.NavigationHelper.DETAIL_EXTRA
import com.mohfahmi.storyapp.core.utils.NavigationHelper.DETAIL_ROUTE
import com.mohfahmi.storyapp.home.adapter.StoriesAdapter
import com.mohfahmi.storyapp.home.databinding.ActivityHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private val binding: ActivityHomeBinding by viewBinding()
    private val viewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
    }

    private fun initViews() {
        with(binding) {
            srlStory.setOnRefreshListener {
                getAllStories()
            }
            rvStory.setLayoutManager(LinearLayoutManager(this@HomeActivity))
            rvStory.addVeiledItems(5)
            getAllStories()

            efbCreateStory.setOnClickListener {
                navigateToAddStory()
            }
            efbStoryMap.setOnClickListener {
                navigateToMapStory()
            }
            efbChangeLanguage.setOnClickListener {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }
            efbLogOut.setOnClickListener {
                logOutAction()
            }
        }
    }

    private fun getAllStories() {
        viewModel.tokenKey.observe(this) { token ->
            viewModel.getAllStories(token).observe(this, ::manageAllStoriesResponse)
        }
    }

    private fun manageAllStoriesResponse(response: UiState<ArrayList<Story>>) {
        with(binding) {
            when (response.status) {
                Status.LOADING -> {
                    rvStory.veil()
                    llLayoutError.invisible()
                    srlStory.isRefreshing = false
                }
                Status.HIDE_LOADING -> {
                    rvStory.unVeil()
                }
                Status.SUCCESS -> {
                    llLayoutError.invisible()
                    val adapter = response.data?.let {
                        StoriesAdapter(it) { story, imgStory, name, description ->
                            val intent = Intent(
                                this@HomeActivity,
                                Class.forName(DETAIL_ROUTE)
                            )
                            intent.putExtra(DETAIL_EXTRA, story)

                            val optionsCompat: ActivityOptionsCompat =
                                ActivityOptionsCompat.makeSceneTransitionAnimation(
                                    this@HomeActivity,
                                    Pair(imgStory, "image"),
                                    Pair(name, "name"),
                                    Pair(description, "description"),
                                )
                            startActivity(intent, optionsCompat.toBundle())
                        }
                    }
                    rvStory.setAdapter(adapter)
                }
                Status.ERROR -> {
                    llLayoutError.visible()
                    tvErrorMessage.text = response.message
                    btnTryAgain.setOnClickListener {
                        getAllStories()
                    }
                }
            }
        }
    }

    private fun logOutAction() {
        startActivity(
            Intent(
                this@HomeActivity,
                Class.forName(NavigationHelper.AUTH_ROUTE)
            )
        )
        viewModel.logOut()
        finish()
    }

    private fun navigateToAddStory() {
        startActivity(
            Intent(
                this@HomeActivity,
                Class.forName(NavigationHelper.ADD_STORY_ROUTE)
            )
        )
    }

    private fun navigateToMapStory() {
        startActivity(
            Intent(
                this@HomeActivity,
                Class.forName(NavigationHelper.STORY_MAP_ROUTE)
            )
        )
    }

}