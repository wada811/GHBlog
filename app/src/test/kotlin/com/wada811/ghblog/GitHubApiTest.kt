package com.wada811.ghblog

import com.wada811.ghblog.data.repository.UserDataRepository
import com.wada811.ghblog.model.domain.Content
import com.wada811.ghblog.model.domain.GitTree
import com.wada811.ghblog.model.domain.Repository
import junit.framework.Assert.assertEquals
import org.junit.Test

class GitHubApiTest {

    @Test
    fun getRepositoryList() {
        UserDataRepository.user().subscribe { user ->
            user.repositoryList.subscribe { repositoryList ->
                repositoryList.forEach { repository ->
                    assertEquals("repository", repository.toString())
                }
            }
        }
    }

    @Test
    fun getContents() {
        UserDataRepository.user().subscribe({ user ->
            user.repositoryList.subscribe { repositoryList ->
                val repository = repositoryList.first { repository ->
                    System.out.println("repository.name: ${repository.name}")
                    repository.name.equals("wada811.com")
                }
                repository.getContents(user, "content/blog").subscribe({ contents: List<Content> ->
                    for (content in contents) {
                        System.out.println("content.name: ${content.name}")
                    }
                    assertEquals("onNext", "onNext")
                }, { System.out.println("error: $it") }, {})
            }
        }, { System.out.println("error: $it") }, {})
    }

    @Test
    fun getTree() {
        UserDataRepository.user().subscribe({ user ->
            user.repositoryList.subscribe { repositoryList ->
                val repository = repositoryList.first { repository ->
                    System.out.println("repository.name: ${repository.name}")
                    repository.name.equals("wada811.com")
                }
                repository.getTree(user).subscribe({ tree: GitTree ->
                    assertEquals("tree", tree.toString())
                }, { System.out.println("error: $it") }, {})
            }
        }, { System.out.println("error: $it") }, {})
    }
}