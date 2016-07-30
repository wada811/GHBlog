package com.wada811.ghblog

import com.wada811.ghblog.data.datasource.user.RemoteUserDataSource
import com.wada811.ghblog.data.repository.GitHubDataRepository
import com.wada811.ghblog.data.repository.UserDataRepository
import com.wada811.ghblog.domain.GHBlogContext
import com.wada811.ghblog.domain.model.GitHubApp
import com.wada811.logforest.LogForest
import com.wada811.logforest.LogLevel
import com.wada811.logforest.LogTree
import com.wada811.logforest.LogWood
import com.wada811.observablemodel.extensions.ObserveCollection
import com.wada811.rxviewmodel.UIThreadScheduler
import com.wada811.rxviewmodel.extensions.ObserveProperty
import com.wada811.rxviewmodel.extensions.toRxProperty
import junit.framework.Assert.assertNotNull
import org.junit.After
import org.junit.Before
import org.junit.Test
import rx.schedulers.Schedulers

class GitHubApiTest {
    val logTree = object : LogTree() {
        override fun log(level: LogLevel, tag: String, message: String, t: Throwable?) {
            System.out.println("$tag: $message")
        }
    }

    @Before
    fun setUp() {
        LogForest.plant(logTree)
        LogWood.d("GHBlogContext.init")
        UIThreadScheduler.DefaultScheduler = Schedulers.immediate()
        val gitHubApp = GitHubApp(BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET)
        val userRepository = UserDataRepository(RemoteUserDataSource(), RemoteUserDataSource())
        val gitHubRepository = GitHubDataRepository()
        GHBlogContext.init(gitHubApp, userRepository, gitHubRepository)
        GHBlogContext.currentUser.loadRepositories()
    }

    @After
    fun tearDown() {
        LogForest.fell(logTree)
    }

    @Test
    fun getRepositoryList() {
        GHBlogContext.currentUser.repositories.ObserveCollection().subscribe {
            assertNotNull(GHBlogContext.currentUser.repositories)
        }
    }

    @Test
    fun getContents() {
        GHBlogContext.currentUser.repositories.ObserveCollection().subscribe {
            val repository = GHBlogContext.currentUser.repositories.first { repository ->
                LogWood.d("repository.name: ${repository.name}")
                repository.name.equals("blogtest")
            }
            GHBlogContext.currentUser.currentRepository = repository
            repository.loadContents("content/blog")
            repository.repositoryContents.ObserveCollection().subscribe {
                assertNotNull(it)
            }
        }
    }


    @Test
    fun getContent() {
        GHBlogContext.currentUser.repositories.ObserveCollection().subscribe {
            val repository = GHBlogContext.currentUser.repositories.first { it.name.equals("blogtest") }
            GHBlogContext.currentUser.currentRepository = repository
            repository.loadContents("content/blog")
            repository.repositoryContents.ObserveCollection().subscribe {
                val repositoryContent = repository.repositoryContents.first()
                repositoryContent.loadContent()
                repositoryContent.ObserveProperty("content", { it.content }).subscribe {
                    assertNotNull(it)
                }
            }
        }
    }

    @Test
    fun createContent() {
        GHBlogContext.currentUser.repositories.ObserveCollection().subscribe {
            val repository = GHBlogContext.currentUser.repositories.first { it.name.equals("blogtest") }
            GHBlogContext.currentUser.currentRepository = repository
            repository.createContent("content/blog/test.md", "create test message", "create content body")
        }
    }

    @Test
    fun updateContent() {
        GHBlogContext.currentUser.repositories.ObserveCollection().subscribe {
            val repository = GHBlogContext.currentUser.repositories.first { it.name.equals("blogtest") }
            GHBlogContext.currentUser.currentRepository = repository
            repository.loadContents("content/blog")
            repository.repositoryContents.ObserveCollection().subscribe {
                val content = repository.repositoryContents.first { it.name == "test.md" }
                content.update(content.path, "update test message", "update content body")
                content.ObserveProperty("sha", { it.sha }).toRxProperty(content.sha).asObservable().subscribe {
                    assertNotNull(it)
                }
            }
        }
    }

    @Test
    fun deleteContent() {
        GHBlogContext.currentUser.repositories.ObserveCollection().subscribe {
            val repository = GHBlogContext.currentUser.repositories.first { it.name.equals("blogtest") }
            GHBlogContext.currentUser.currentRepository = repository
            repository.loadContents("content/blog")
            repository.repositoryContents.ObserveCollection().subscribe {
                val content = repository.repositoryContents.first { it.name == "test.md" }
                content.delete("delete test message", content.content)
                content.ObserveProperty("sha", { it.sha }).toRxProperty(content.sha).asObservable().subscribe {
                    assertNotNull(it)
                }
            }
        }
    }

    @Test
    fun renameContent() {
        GHBlogContext.currentUser.repositories.ObserveCollection().subscribe {
            val repository = GHBlogContext.currentUser.repositories.first { it.name.equals("blogtest") }
            GHBlogContext.currentUser.currentRepository = repository
            repository.loadContents("content/blog")
            repository.repositoryContents.ObserveCollection().subscribe {
                val content = repository.repositoryContents.first { it.name == "rename.md" || it.name == "renamed.md" }
                val path = content.path.replace("rename.md", "tmp.md")
                    .replace("renamed.md", "rename.md")
                    .replace("tmp.md", "renamed.md")
                content.update(path, "rename ${content.name}", "rename content")
                content.ObserveProperty("sha", { it.sha }).toRxProperty(content.sha).asObservable().subscribe {
                    assertNotNull(it)
                }
            }
        }
    }

    @Test
    fun getTree() {
        GHBlogContext.currentUser.repositories.ObserveCollection().subscribe {
            val repository = GHBlogContext.currentUser.repositories.first { it.name.equals("blogtest") }
            GHBlogContext.currentUser.currentRepository = repository
            repository.getTree().subscribe {
                assertNotNull(it)
            }
        }
    }
}