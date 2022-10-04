package com.mohfahmi.storyapp.core.data.source.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mohfahmi.storyapp.core.data.source.remote.api.ApiService
import com.mohfahmi.storyapp.core.domain.models.Story
import com.mohfahmi.storyapp.core.utils.mapToDomain
import retrofit2.HttpException
import java.io.IOException

class StoryPagingSource(private val apiService: ApiService, private val token: String) :
    PagingSource<Int, Story>() {
    override fun getRefreshKey(state: PagingState<Int, Story>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Story> {
        return try {
            val position = params.key ?: STARTING_PAGE_INDEX
            val response =
                apiService.getStories("Bearer $token", position, params.loadSize).body()
                    .mapToDomain()
            LoadResult.Page(
                data = response,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (response.isEmpty()) null else position + 1
            )
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        }
    }

    private companion object {
        const val STARTING_PAGE_INDEX = 1
    }
}