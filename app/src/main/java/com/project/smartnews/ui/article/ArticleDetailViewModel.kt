package com.project.smartnews.ui.article

import androidx.lifecycle.ViewModel
import com.project.smartnews.data.repo.ArticleRepository
import com.project.smartnews.model.ArticleEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ArticleDetailViewModel @Inject constructor(
    private val articleRepository: ArticleRepository
) : ViewModel() {

    var articleId: Int = 0

    fun getArticleById(articleId: Int): Flow<ArticleEntity> {
        return articleRepository.getArticleById(articleId)
    }

    //CNN for (text in doc.select("div.zn-body__paragraph")) {
    // println(text.text())}
    fun scrapeContentFromWeb(url: String, sourceName: String): String {
        var content: String = ""

        try {
            val doc: Document = Jsoup.connect(url).get()

            when (sourceName) {
                "CNN" -> {
                    for (text in doc.select("div.zn-body__paragraph")) {
                        content += text.text()
                        content += "\n\n\n"
                    }
                }
                "TechCrunch" -> {
                    for (text in doc.select("div")) {
                        for(t in text.select(".article-content")){
                            for (a in t.select("p")) {
                                content += a.text()
                            }
                            content += "\n\n\n"
                        }
                    }
                }
                "Fox News" -> {
                    for (t in doc.select(".article-content")) {
                        for (a in t.select("p")) {
                            content += a.allElements.text()
                        }
                        content += "\n\n"
                    }
                }
                "The Verge" -> {
                    for (t in doc.select("div.c-entry-content")) {
                        for ( a in t.select("p")) {
                            content += a.allElements.text()
                        }
                        content += "\n\n"
                    }
                }
                "The Next Web" -> {
                    for (text in doc.select("div")) {
                        for (t in text.select(".c-richText")) {
                            for(a in t.select("p")) {
                                content += a.text()
                            }
                            content += "\n\n"
                        }
                    }
                }
                else -> {
                    content = "Not Implemented yet."
                }
            }
        } catch (e: IOException) {
            content = "${e.printStackTrace()}"
        }

        return content
    }
}