package com.wada811.ghblog

import com.wada811.ghblog.data.repository.UserDataRepository
import com.wada811.ghblog.model.domain.Repository
import junit.framework.Assert.assertEquals
import org.junit.Test

class GitHubApiTest {

    @Test
    fun getRepositoryList() {
        UserDataRepository.user().subscribe { user ->
            user.repositoryList.subscribe { repositories ->
                val repositoryList = repositories as List<*>
                repositoryList.forEach { element ->
                    val repository = element as Repository
                    assertEquals("repository", repository.toString())
                }
            }
        }
    }
}