package com.wada811.ghblog

import com.wada811.ghblog.data.repository.UserDataRepository
import com.wada811.ghblog.model.domain.GitCommit
import com.wada811.ghblog.model.domain.GitHubTree
import com.wada811.ghblog.model.domain.RepositoryContent
import com.wada811.ghblog.model.domain.RepositoryContentInfo
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
                    repository.name.equals("blogtest")
                }
                repository.getContents(user, "content/blog").subscribe({ repositoryContentInfos: List<RepositoryContentInfo> ->
                    for (content in repositoryContentInfos) {
                        System.out.println("content.name: ${content.name}")
                    }
                    assertEquals("onNext", "onNext")
                }, { System.out.println("error: $it") }, {})
            }
        }, { System.out.println("error: $it") }, {})
    }


    @Test
    fun getContent() {
        UserDataRepository.user().subscribe({ user ->
            user.repositoryList.subscribe { repositoryList ->
                val repository = repositoryList.first { repository ->
                    System.out.println("repository.name: ${repository.name}")
                    repository.name.equals("blogtest")
                }
                repository.getContents(user, "content/blog").subscribe({ repositoryContentInfoList: List<RepositoryContentInfo> ->
                    repositoryContentInfoList.first { it.name.equals("undo-git-reset.md") }
                            .getContent(user, repository)
                            .subscribe({ content: RepositoryContent ->
                                System.out.println("content.encoding: ${content.encoding}")
                                System.out.println("content.content: ${content.encodedContent}")
                                assertEquals("onNext", content.content)
                            }, { System.out.println("error: $it") }, {})
                }, { System.out.println("error: $it") }, {})
            }
        }, { System.out.println("error: $it") }, {})
    }

    @Test
    fun createContent() {
        UserDataRepository.user().subscribe({ user ->
            user.repositoryList.subscribe({ repositoryList ->
                val repository = repositoryList.first { repository ->
                    System.out.println("repository.name: ${repository.name}")
                    repository.name.equals("blogtest")
                }
                repository.createContent(user, GitCommit("content/blog/test.md", "create test message", "create content body"))
                        .subscribe({ gitHubCommit ->
                            System.out.println("gitHubCommit: $gitHubCommit")
                            assertEquals("onNext", gitHubCommit)
                        }, { System.out.println("error: $it") }, {})
            }, { System.out.println("error: $it") }, {})
        }, { System.out.println("error: $it") }, {})
    }

    @Test
    fun updateContent() {
        UserDataRepository.user().subscribe({ user ->
            user.repositoryList.subscribe({ repositoryList ->
                val repository = repositoryList.first { repository ->
                    System.out.println("repository.name: ${repository.name}")
                    repository.name.equals("blogtest")
                }
                val path = "content/blog/test.md"
                repository.getContent(user, path).subscribe({
                    repository.updateContent(user, GitCommit(path, "update test message", "update content body", it.sha))
                            .subscribe({ gitHubCommit ->
                                System.out.println("gitHubCommit: $gitHubCommit")
                                assertEquals("onNext", gitHubCommit)
                            }, { System.out.println("error: $it") }, {})
                }, { System.out.println("error: $it") }, {})
            }, { System.out.println("error: $it") }, {})
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
                repository.getTree(user).subscribe({ tree: GitHubTree ->
                    assertEquals("tree", tree.toString())
                }, { System.out.println("error: $it") }, {})
            }
        }, { System.out.println("error: $it") }, {})
    }
}