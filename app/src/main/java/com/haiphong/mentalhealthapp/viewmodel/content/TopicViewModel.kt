package com.haiphong.mentalhealthapp.viewmodel.content

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.haiphong.mentalhealthapp.model.Article
import com.haiphong.mentalhealthapp.model.Post
import com.haiphong.mentalhealthapp.model.topics
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class ArticlesListState(
    val articlesList: List<Article> = listOf(),
    val status: String = "loading"
)

class TopicViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    private val _postsList = MutableStateFlow<List<Post>>(listOf())
    val postsList = _postsList.asStateFlow()

    private var _articlesState = MutableStateFlow(ArticlesListState())
    val articlesState = _articlesState.asStateFlow()


    private val topicName: String = checkNotNull(savedStateHandle["topicName"])

    init {
        getPostsList()
        getArticles()
    }

    private fun getPostsList() {
        for (topic in topics) {
            if (topic.name == topicName) {
                _postsList.value = topic.postsList
            }
        }
    }

    private fun getArticles() {
        // mot list mau
        // update list trong nay thanh list day
        for (topic in topics) {
            if (topic.name == topicName) {
                _articlesState.update {
                    it.copy(
                        status = "done",
                        articlesList = topic.articlesList
                    )
                }
            }
        }

    }

    /*
    private fun getArticles() {
        try {
            val options = ChromeOptions()
            options.addArguments("--headless")
            val driver = ChromeDriver(options)
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2))
            val wait = WebDriverWait(driver, Duration.ofSeconds(2))

            driver.get("https://www.google.com/")

            val searchBar = driver.findElement(By.name("q"))
            searchBar.sendKeys("$topicName articles")
            searchBar.submit()

            val searchResults = driver.findElements(By.cssSelector("div.g"))
            wait.until { searchResults.size != 0 }

            for (i in 0 until 5) {
                val title = searchResults[i].findElement(By.cssSelector("h3")).text
                val url = searchResults[i].findElement(By.cssSelector("a")).getAttribute("href")
                _articlesState.update {
                    it.copy(
                        articlesList = listOf(
                            *(_articlesState.value.articlesList.toTypedArray()),
                            Article(title = title, url = url)
                        )
                    )
                }
            }

            _articlesState.update {
                it.copy(
                    status = "done"
                )
            }

            driver.quit()
        } catch (e: RuntimeException) {
            Log.d("Get Articles", "Failed")
            _articlesState.update {
                it.copy(
                    status = "error"
                )
            }
        }

    }
     */
}