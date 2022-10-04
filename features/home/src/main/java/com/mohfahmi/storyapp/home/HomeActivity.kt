package com.mohfahmi.storyapp.home

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.viewbinding.library.activity.viewBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.mohfahmi.storyapp.core.domain.models.Story
import com.mohfahmi.storyapp.core.utils.NavigationHelper
import com.mohfahmi.storyapp.core.utils.NavigationHelper.DETAIL_EXTRA
import com.mohfahmi.storyapp.core.utils.NavigationHelper.DETAIL_ROUTE
import com.mohfahmi.storyapp.core.utils.invisible
import com.mohfahmi.storyapp.core.utils.visible
import com.mohfahmi.storyapp.home.adapter.LoadingStateAdapter
import com.mohfahmi.storyapp.home.adapter.StoriesAdapter
import com.mohfahmi.storyapp.home.databinding.ActivityHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private val binding: ActivityHomeBinding by viewBinding()
    private val viewModel: HomeViewModel by viewModel()
    private val adapter: StoriesAdapter = StoriesAdapter { story, imgStory, name, description ->
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
        binding.rvStory.setAdapter(adapter.withLoadStateFooter(footer = LoadingStateAdapter {
            adapter.retry()
        }))
        viewModel.tokenKey.observe(this) { token ->
            viewModel.getAllStories(token).observe(this, ::manageAllStoriesResponse)
        }
    }

    private fun manageAllStoriesResponse(response: PagingData<Story>) {
        adapter.submitData(lifecycle, response)

        adapter.addLoadStateListener { loadState ->
            with(binding) {
                if (loadState.mediator?.refresh is LoadState.Loading) {
                    rvStory.visible()
                    rvStory.veil()
                    llLayoutError.invisible()
                    srlStory.isRefreshing = false
                } else {
                    rvStory.unVeil()
                    srlStory.isRefreshing = false
                    val error = when {
                        loadState.mediator?.prepend is LoadState.Error ->
                            loadState.mediator?.prepend as LoadState.Error
                        loadState.mediator?.append is LoadState.Error ->
                            loadState.mediator?.append as LoadState.Error
                        loadState.mediator?.refresh is LoadState.Error ->
                            loadState.mediator?.refresh as LoadState.Error
                        else -> null
                    }
                    error?.let {
                        if (adapter.snapshot().isEmpty()) {
                            llLayoutError.visible()
                            rvStory.invisible()
                            tvErrorMessage.text = it.error.localizedMessage
                            btnTryAgain.setOnClickListener {
                                getAllStories()
                            }
                        }
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