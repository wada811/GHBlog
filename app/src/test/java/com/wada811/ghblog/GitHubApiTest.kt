package com.wada811.ghblog

import com.wada811.ghblog.data.repository.GitHubDataRepository
import com.wada811.ghblog.data.repository.UserDataRepository
import com.wada811.ghblog.domain.GHBlogContext
import com.wada811.ghblog.domain.model.RepositoryContentInfo
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertNull
import org.junit.Before
import org.junit.Test

class GitHubApiTest {

    @Before
    fun init() {
        GHBlogContext.userRepository = UserDataRepository()
        GHBlogContext.gitHubRepository = GitHubDataRepository()
    }

    @Test
    fun getRepositoryList() {
        GHBlogContext.userRepository.user().subscribe { user ->
            user.repositoryList.subscribe({
                it.forEach { System.out.println("onNext: ${it.fullName}") }
                assertNotNull(it)
            }, {
                System.out.println("error: $it")
                it.printStackTrace()
                assertNull(it)
            })
        }
    }

    @Test
    fun getContents() {
        GHBlogContext.userRepository.user().subscribe({ user ->
            user.repositoryList.subscribe { repositoryList ->
                val repository = repositoryList.first { repository ->
                    System.out.println("repository.name: ${repository.name}")
                    repository.name.equals("blogtest")
                }
                repository.getContents(user, "content/blog").subscribe {
                    it.forEach { System.out.println("content.name: ${it.name}") }
                    assertNotNull(it)
                }
            }
        }, {
            System.out.println("error: $it")
            it.printStackTrace()
            assertNull(it)
        })
    }


    @Test
    fun getContent() {
        GHBlogContext.userRepository.user().subscribe({ user ->
            user.repositoryList.subscribe { repositoryList ->
                val repository = repositoryList.first { it.name.equals("blogtest") }
                repository.getContents(user, "content/blog").subscribe { repositoryContentInfoList: List<RepositoryContentInfo> ->
                    repositoryContentInfoList.first()
                        .getContent(user, repository)
                        .subscribe {
                            assertNotNull(it)
                        }
                }
            }
        }, {
            System.out.println("error: $it")
            it.printStackTrace()
            assertNull(it)
        })
    }

    @Test
    fun createContent() {
        GHBlogContext.userRepository.user().subscribe({ user ->
            user.repositoryList.subscribe { repositoryList ->
                val repository = repositoryList.first { it.name.equals("blogtest") }
                repository.createContent("content/blog/test.md", "create test message", "create content body")
                    .subscribe {
                        System.out.println("onNext: $it")
                        assertNotNull(it)
                    }
            }
        }, {
            System.out.println("error: $it")
            it.printStackTrace()
            assertNull(it)
        })
    }

    @Test
    fun updateContent() {
        GHBlogContext.userRepository.user().subscribe({ user ->
            user.repositoryList.subscribe { repositoryList ->
                val repository = repositoryList.first { it.name.equals("blogtest") }
                val path = "content/blog/test.md"
                repository.getContent(user, path).subscribe {
                    it.update(path, "update test message", "update content body")
                        .subscribe {
                            assertNotNull(it)
                        }
                }
            }
        }, {
            System.out.println("error: $it")
            it.printStackTrace()
            assertNull(it)
        })
    }

    @Test
    fun deleteContent() {
        GHBlogContext.userRepository.user().subscribe({ user ->
            user.repositoryList.subscribe { repositoryList ->
                val repository = repositoryList.first { it.name.equals("blogtest") }
                val path = "content/blog/test.md"
                repository.getContent(user, path).subscribe {
                    it.delete("delete test message", it.content)
                        .subscribe {
                            assertNotNull(it)
                        }
                }
            }
        }, {
            System.out.println("error: $it")
            it.printStackTrace()
            assertNull(it)
        })
    }

    @Test
    fun renameContent() {
        GHBlogContext.userRepository.user().subscribe({ user ->
            user.repositoryList.subscribe { repositoryList ->
                val repository = repositoryList.first { it.name.equals("blogtest") }
                val path = "content/blog/rename.md"
                repository.getContent(user, path).subscribe {
                    it.update("content/blog/renamed.md", "rename rename.md", "rename content")
                        .subscribe {
                            assertNotNull(it)
                        }
                }
            }
        }, {
            System.out.println("error: $it")
            it.printStackTrace()
            assertNull(it)
        })
    }

    @Test
    fun getTree() {
        GHBlogContext.userRepository.user().subscribe({ user ->
            user.repositoryList.subscribe { repositoryList ->
                val repository = repositoryList.first { it.name.equals("blogtest") }
                repository.getTree(user).subscribe {
                    assertNotNull(it)
                }
            }
        }, {
            System.out.println("error: $it")
            it.printStackTrace()
            assertNull(it)
        })
    }
}