package com.wada811.ghblog

import com.wada811.ghblog.data.repository.GitHubDataRepository
import com.wada811.ghblog.data.repository.UserDataRepository
import com.wada811.ghblog.domain.GHBlogContext
import com.wada811.ghblog.domain.model.RepositoryContentInfo
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
            repository.getContents("content/blog").subscribe {
                it.forEach { System.out.println("content.name: ${it.name}") }
                assertNotNull(it)
            }
        }
    }


    @Test
    fun getContent() {
        GHBlogContext.currentUser.repositories.ObserveCollection().subscribe {
            val repository = GHBlogContext.currentUser.repositories.first { it.name.equals("blogtest") }
            GHBlogContext.currentUser.currentRepository = repository
            repository.getContents("content/blog").subscribe { repositoryContentInfoList: List<RepositoryContentInfo> ->
                repositoryContentInfoList.first()
                    .getContent()
                    .subscribe {
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
                .subscribe {
                    System.out.println("onNext: $it")
                    assertNotNull(it)
                }
        }
    }

    @Test
    fun updateContent() {
        GHBlogContext.currentUser.repositories.ObserveCollection().subscribe {
            val repository = GHBlogContext.currentUser.repositories.first { it.name.equals("blogtest") }
            GHBlogContext.currentUser.currentRepository = repository
            val path = "content/blog/test.md"
            repository.getContent(path).subscribe {
                it.update(path, "update test message", "update content body")
                it.ObserveProperty("sha", { it.sha }).toRxProperty(it.sha).asObservable().subscribe { assertNotNull(it) }
            }
        }
    }

    @Test
    fun deleteContent() {
        GHBlogContext.currentUser.repositories.ObserveCollection().subscribe {
            val repository = GHBlogContext.currentUser.repositories.first { it.name.equals("blogtest") }
            GHBlogContext.currentUser.currentRepository = repository
            repository.getContent("content/blog/test.md").subscribe {
                it.delete("delete test message", it.content)
                    .subscribe {
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
            repository.getContent("content/blog/rename.md").subscribe({
                it.update("content/blog/renamed.md", "rename rename.md", "rename content")
                it.ObserveProperty("sha", { it.sha }).toRxProperty(it.sha).asObservable().subscribe { assertNotNull(it) }
            }, {
                repository.getContent("content/blog/renamed.md").subscribe {
                    it.update("content/blog/rename.md", "rename renamed.md", "rename content")
                    it.ObserveProperty("sha", { it.sha }).toRxProperty(it.sha).asObservable().subscribe { assertNotNull(it) }
                }
            })
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