package com.wada811.ghblog

import com.wada811.ghblog.data.repository.GitHubDataRepository
import com.wada811.ghblog.data.repository.UserDataRepository
import com.wada811.ghblog.domain.GHBlogContext
import com.wada811.ghblog.domain.model.RepositoryContentInfo
import com.wada811.rxviewmodel.UIThreadScheduler
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
        assertNotNull(GHBlogContext.currentUser.repositories)
    }

    @Test
    fun getContents() {
        val repository = GHBlogContext.currentUser.repositories.first { repository ->
            System.out.println("repository.name: ${repository.name}")
            repository.name.equals("blogtest")
        }
        GHBlogContext.currentUser.currentRepository = repository
        repository.getContents(GHBlogContext.currentUser, "content/blog").subscribe {
            it.forEach { System.out.println("content.name: ${it.name}") }
            assertNotNull(it)
        }
    }


    @Test
    fun getContent() {
        val repository = GHBlogContext.currentUser.repositories.first { it.name.equals("blogtest") }
        GHBlogContext.currentUser.currentRepository = repository
        repository.getContents(GHBlogContext.currentUser, "content/blog").subscribe { repositoryContentInfoList: List<RepositoryContentInfo> ->
            repositoryContentInfoList.first()
                .getContent(GHBlogContext.currentUser, repository)
                .subscribe {
                    assertNotNull(it)
                }
        }
    }

    @Test
    fun createContent() {
        val repository = GHBlogContext.currentUser.repositories.first { it.name.equals("blogtest") }
        GHBlogContext.currentUser.currentRepository = repository
        repository.createContent("content/blog/test.md", "create test message", "create content body")
            .subscribe {
                System.out.println("onNext: $it")
                assertNotNull(it)
            }
    }

    @Test
    fun updateContent() {
        val repository = GHBlogContext.currentUser.repositories.first { it.name.equals("blogtest") }
        GHBlogContext.currentUser.currentRepository = repository
        val path = "content/blog/test.md"
        repository.getContent(GHBlogContext.currentUser, path).subscribe {
            it.update(path, "update test message", "update content body")
                .subscribe {
                    assertNotNull(it)
                }
        }
    }

    @Test
    fun deleteContent() {
        val repository = GHBlogContext.currentUser.repositories.first { it.name.equals("blogtest") }
        GHBlogContext.currentUser.currentRepository = repository
        val path = "content/blog/test.md"
        repository.getContent(GHBlogContext.currentUser, path).subscribe {
            it.delete("delete test message", it.content)
                .subscribe {
                    assertNotNull(it)
                }
        }
    }

    @Test
    fun renameContent() {
        val repository = GHBlogContext.currentUser.repositories.first { it.name.equals("blogtest") }
        GHBlogContext.currentUser.currentRepository = repository
        repository.getContent(GHBlogContext.currentUser, "content/blog/rename.md").subscribe({
            it.update("content/blog/renamed.md", "rename rename.md", "rename content")
                .subscribe {
                    assertNotNull(it)
                }
        }, {
            repository.getContent(GHBlogContext.currentUser, "content/blog/renamed.md").subscribe {
                it.update("content/blog/rename.md", "rename renamed.md", "rename content")
                    .subscribe {
                        assertNotNull(it)
                    }
            }
        })
    }

    @Test
    fun getTree() {
        val repository = GHBlogContext.currentUser.repositories.first { it.name.equals("blogtest") }
        GHBlogContext.currentUser.currentRepository = repository
        repository.getTree(GHBlogContext.currentUser).subscribe {
            assertNotNull(it)
        }
    }
}