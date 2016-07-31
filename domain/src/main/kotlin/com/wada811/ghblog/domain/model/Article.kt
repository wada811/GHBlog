package com.wada811.ghblog.domain.model

import com.wada811.logforest.LogWood
import com.wada811.observablemodel.ObservableSynchronizedArrayList
import com.wada811.observablemodel.events.property.INotifyPropertyChanged
import com.wada811.observablemodel.events.property.PropertyChangedDelegate
import com.wada811.observablemodel.extensions.PropertyChangedAsObservable
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import rx.Subscription
import rx.subscriptions.CompositeSubscription

class Article(
    val user: User,
    val repository: Repository,
    filePath: String,
    publishDateTime: ZonedDateTime = ZonedDateTime.now(),
    isDraft: Boolean,
    title: String,
    tags: ObservableSynchronizedArrayList<String>,
    body: String,
    repositoryContent: RepositoryContent? = null
) : INotifyPropertyChanged, Subscription {
    var filePath: String by PropertyChangedDelegate(filePath)
    var publishDateTime: ZonedDateTime by PropertyChangedDelegate(publishDateTime)
    var isDraft: Boolean by PropertyChangedDelegate(isDraft)
    var title: String by PropertyChangedDelegate(title)
    val tags: ObservableSynchronizedArrayList<String> by PropertyChangedDelegate(tags)
    var body: String by PropertyChangedDelegate(body)
    var repositoryContent: RepositoryContent? by PropertyChangedDelegate(repositoryContent)

    private val subscriptions = CompositeSubscription()
    override fun isUnsubscribed(): Boolean = subscriptions.isUnsubscribed
    override fun unsubscribe() {
        if (!isUnsubscribed) {
            subscriptions.unsubscribe()
        }
    }

    init {
        if (repositoryContent != null) {
            subscriptions.add(
                repositoryContent.PropertyChangedAsObservable().subscribe {
                    if (it.PropertyName == "content") {
                        this.publishDateTime = Article.Builder.parsePublishDateTime(repositoryContent.content)
                        this.isDraft = Article.Builder.parseIsDraft(repositoryContent.content)
                        this.title = Article.Builder.parseTitle(repositoryContent.content)
                        this.tags.clear()
                        this.tags.addAll(Article.Builder.parseTags(repositoryContent.content))
                        this.body = Article.Builder.parseBody(repositoryContent.content)
                    } else if (it.PropertyName == "path") {
                        this.filePath = repositoryContent.path
                    }
                }
            )
        }
    }


    fun save() {
        publishDateTime = ZonedDateTime.now()
        if (repositoryContent != null) {
            repositoryContent!!.update(filePath, "Edit an article: $title", formatArticleContent())
        } else {
            repository.createContent(filePath, "Write an article: $title", formatArticleContent())
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

    class Builder(private var user: User, private var repository: Repository, private var repositoryContent: RepositoryContent? = null) {
        private var filePath: String = ""
        private var publishDateTime: ZonedDateTime = ZonedDateTime.now()
        private var isDraft: Boolean = false
        private var title: String = ""
        private var tags: ObservableSynchronizedArrayList<String> = ObservableSynchronizedArrayList()
        private var body: String = ""

        companion object {
            fun repositoryContent(repositoryContent: RepositoryContent): Build {
                LogWood.v("repositoryContent.content: ${repositoryContent.content}")
                val publishDateTime = parsePublishDateTime(repositoryContent.content)
                val isDraft = parseIsDraft(repositoryContent.content)
                val title = parseTitle(repositoryContent.content)
                val tags = parseTags(repositoryContent.content)
                val body = parseBody(repositoryContent.content)
                return Article.Builder(repositoryContent.user, repositoryContent.repository, repositoryContent)
                    .filePath(repositoryContent.path)
                    .publishDateTime(publishDateTime)
                    .isDraft(isDraft)
                    .title(title)
                    .tags(tags)
                    .body(body)
            }

            fun parsePublishDateTime(content: String): ZonedDateTime {
                if(content == ""){
                    return ZonedDateTime.now()
                }
                val contents = content.split("+++")
                val metaInfo = contents[1].split(System.getProperty("line.separator"))
                return ZonedDateTime.parse(
                    Regex("""date = "(.+)"""").find(metaInfo.first { it.startsWith("date") })!!.groupValues.last(),
                    DateTimeFormatter.ISO_OFFSET_DATE_TIME
                )
            }

            fun parseIsDraft(content: String): Boolean {
                if(content == ""){
                    return false
                }
                val contents = content.split("+++")
                val metaInfo = contents[1].split(System.getProperty("line.separator"))
                return Regex("""draft = (.+)""").find(metaInfo.first { it.startsWith("draft") })!!.groupValues.last().toBoolean()
            }

            fun parseTitle(content: String): String {
                if(content == ""){
                    return ""
                }
                val contents = content.split("+++")
                val metaInfo = contents[1].split(System.getProperty("line.separator"))
                return Regex("""title = "(.+)"""").find(metaInfo.first { it.startsWith("title") })!!.groupValues.last()
            }

            fun parseTags(content: String): ObservableSynchronizedArrayList<String> {
                if(content == ""){
                    return ObservableSynchronizedArrayList()
                }
                val contents = content.split("+++")
                val metaInfo = contents[1]
                val tags = Regex(""".*tags = \[((?:.|\r|\n)+)].*""").find(metaInfo)!!.groupValues.last().trim().split(Regex(",[[:blank:]]*"))
                LogWood.v("tags: $tags")
                LogWood.v("tags.size: ${tags.size}")
                if (tags.size == 1) {
                    return ObservableSynchronizedArrayList()
                } else {
                    return ObservableSynchronizedArrayList(tags.map { it.substring(1, it.lastIndex) })
                }
            }

            fun parseBody(content: String): String {
                if(content == ""){
                    return ""
                }
                val contents = content.split("+++")
                return contents[2].trim()
            }
        }

        fun filePath(filePath: String): FilePath {
            this.filePath = filePath
            return FilePath(this)
        }

        class FilePath(private var builder: Builder) {
            fun publishDateTime(publishDateTime: ZonedDateTime): PublishDateTime {
                builder.publishDateTime = publishDateTime
                return PublishDateTime(builder)
            }
        }

        class PublishDateTime(private var builder: Builder) {
            fun isDraft(isDraft: Boolean): Title {
                builder.isDraft = isDraft
                return Title(builder)
            }
        }

        class Title(private var builder: Builder) {
            fun title(title: String): Tags {
                builder.title = title
                return Tags(builder)
            }
        }

        class Tags(private var builder: Builder) {
            fun tags(tags: ObservableSynchronizedArrayList<String>): Body {
                builder.tags = tags
                return Body(builder)
            }
        }

        class Body(private var builder: Builder) {
            fun body(body: String): Build {
                builder.body = body
                return Build(builder)
            }
        }

        class Build(private var builder: Builder) {
            fun build(): Article {
                return Article(
                    builder.user,
                    builder.repository,
                    builder.filePath,
                    builder.publishDateTime,
                    builder.isDraft,
                    builder.title,
                    builder.tags,
                    builder.body,
                    builder.repositoryContent
                )
            }
        }
    }
}