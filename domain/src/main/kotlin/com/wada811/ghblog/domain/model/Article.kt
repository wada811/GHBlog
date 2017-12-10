package com.wada811.ghblog.domain.model

import com.wada811.ghblog.domain.GHBlogContext
import com.wada811.logforest.LogWood
import com.wada811.observablemodel.ObservableSynchronizedArrayList
import com.wada811.observablemodel.events.property.INotifyPropertyChanged
import com.wada811.observablemodel.events.property.PropertyChangedDelegate
import com.wada811.observablemodel.extensions.PropertyChangedAsObservable
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

class Article(
    val blog: Blog,
    repositoryContent: RepositoryContent? = null,
    filePath: String = "",
    publishDateTime: ZonedDateTime = ZonedDateTime.now(),
    isDraft: Boolean = false,
    title: String = "",
    tags: ObservableSynchronizedArrayList<String> = ObservableSynchronizedArrayList(),
    body: String = ""
) : INotifyPropertyChanged {
    var repositoryContent: RepositoryContent? by PropertyChangedDelegate(repositoryContent)
    var filePath: String by PropertyChangedDelegate(filePath)
    var publishDateTime: ZonedDateTime by PropertyChangedDelegate(publishDateTime)
    var isDraft: Boolean by PropertyChangedDelegate(isDraft)
    var title: String by PropertyChangedDelegate(title)
    val tags: ObservableSynchronizedArrayList<String> by PropertyChangedDelegate(tags)
    var body: String by PropertyChangedDelegate(body)

    init {
        if (repositoryContent != null) {
            repositoryContent.loadContent()
            this.filePath = repositoryContent.path
            this.publishDateTime = Article.parsePublishDateTime(repositoryContent.content)
            this.isDraft = Article.parseIsDraft(repositoryContent.content)
            this.title = Article.parseTitle(repositoryContent.content)
            this.tags.clear()
            this.tags.addAll(Article.parseTags(repositoryContent.content))
            this.body = Article.parseBody(repositoryContent.content)
            repositoryContent.PropertyChangedAsObservable().subscribe {
                if (it.PropertyName == "content") {
                    this.publishDateTime = Article.parsePublishDateTime(repositoryContent.content)
                    this.isDraft = Article.parseIsDraft(repositoryContent.content)
                    this.title = Article.parseTitle(repositoryContent.content)
                    this.tags.clear()
                    this.tags.addAll(Article.parseTags(repositoryContent.content))
                    this.body = Article.parseBody(repositoryContent.content)
                } else if (it.PropertyName == "path") {
                    this.filePath = repositoryContent.path
                }
            }
        }
    }


    fun save(filePath: String, isDraft: Boolean, title: String, body: String) {
        this.filePath = filePath
        this.publishDateTime = ZonedDateTime.now()
        this.isDraft = isDraft
        this.title = title
        this.body = body
        if (repositoryContent != null) {
            repositoryContent!!.update(filePath, "Edit an article: $title", formatArticleContent())
        } else {
            blog.createArticle(filePath, "Write an article: $title", formatArticleContent())
            GHBlogContext.gitHubRepository.saveArticle(this)
                .subscribe({
                    blog.articles.add(this)
                })
        }
    }

    fun formatArticleContent(): String {
        return """+++
date = "${publishDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)}"
draft = $isDraft
title = "$title"
tags = [ ${tags.map { """"$it"""" }.joinToString(", ")} ]
+++
$body"""
    }

    companion object {

        private fun parsePublishDateTime(content: String?): ZonedDateTime {
            if (content == null) {
                return ZonedDateTime.now()
            }
            val contents = content.split("+++")
            val metaInfo = contents[1].split(System.getProperty("line.separator"))
            return ZonedDateTime.parse(
                Regex("""date = "(.+)"""").find(metaInfo.first { it.startsWith("date") })!!.groupValues.last(),
                DateTimeFormatter.ISO_OFFSET_DATE_TIME
            )
        }

        private fun parseIsDraft(content: String?): Boolean {
            if (content == null) {
                return false
            }
            val contents = content.split("+++")
            val metaInfo = contents[1].split(System.getProperty("line.separator"))
            return Regex("""draft = (.+)""").find(metaInfo.first { it.startsWith("draft") })!!.groupValues.last().toBoolean()
        }

        private fun parseTitle(content: String?): String {
            if (content == null) {
                return ""
            }
            val contents = content.split("+++")
            val metaInfo = contents[1].split(System.getProperty("line.separator"))
            return Regex("""title = "(.+)"""").find(metaInfo.first { it.startsWith("title") })!!.groupValues.last()
        }

        private fun parseTags(content: String?): ObservableSynchronizedArrayList<String> {
            if (content == null) {
                return ObservableSynchronizedArrayList()
            }
            val contents = content.split("+++")
            val metaInfo = contents[1]
            val tags = Regex(""".*tags = \[((?:.|\r|\n)+)].*""").find(metaInfo)!!.groupValues.last().split(",")
                .map { it.trim().trim { it.equals('"') } }.filter { it.length != 0 }
            LogWood.v("tags: $tags")
            return ObservableSynchronizedArrayList(tags)
        }

        private fun parseBody(content: String?): String {
            if (content == null) {
                return ""
            }
            val contents = content.split("+++")
            return contents[2].trim()
        }
    }
}