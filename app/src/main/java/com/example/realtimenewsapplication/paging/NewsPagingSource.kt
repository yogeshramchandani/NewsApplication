package com.example.realtimenewsapplication.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.realtimenewsapplication.api.NewsAPI
import com.example.realtimenewsapplication.models.Article
import retrofit2.HttpException
import java.io.IOException

class NewsPagingSource(private val newsAPI: NewsAPI) : PagingSource<Int, Article>() {

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val currentPage = params.key ?: 1
            val response = newsAPI.getBreakingNews(pageNumber = currentPage, countryCode = "us")

            if (response.isSuccessful && response.body() != null) {
                val articles = response.body()!!.articles
                LoadResult.Page(
                    data = articles,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (articles.isEmpty()) null else currentPage + 1
                )
            } else {
                LoadResult.Error(HttpException(response))
            }
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}
