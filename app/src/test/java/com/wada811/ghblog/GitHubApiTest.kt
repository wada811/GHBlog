package com.wada811.ghblog

import com.wada811.ghblog.data.repository.GitHubDataRepository
import com.wada811.ghblog.data.repository.UserDataRepository
import com.wada811.ghblog.domain.GHBlogContext
import com.wada811.observablemodel.extensions.ObserveCollection
import com.wada811.rxviewmodel.UIThreadScheduler
import com.wada811.rxviewmodel.extensions.ObserveProperty
import com.wada811.rxviewmodel.extensions.toRxProperty
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import rx.schedulers.Schedulers

class GitHubApiTest {

    @Before
    fun init() {
        System.out.println("GHBlogContext.init")
        GHBlogContext.init(UserDataRepository(), GitHubDataRepository())
        UIThreadScheduler.DefaultScheduler = Schedulers.immediate()
        GHBlogContext.currentUser.loadRepositories()
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
                System.out.println("repository.name: ${repository.name}")
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