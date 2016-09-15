package com.wada811.ghblog.data

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.squareup.moshi.Moshi
import com.wada811.ghblog.data.entity.data.OrmaDatabase
import com.wada811.ghblog.data.entity.mapper.data.UserEntityDataMapper
import com.wada811.ghblog.data.entity.mapper.response.GetUserResponseDataMapper
import com.wada811.ghblog.data.entity.response.github.users.GetUserResponse
import com.wada811.ghblog.data.http.adapter.ZonedDateTimeAdapter
import com.wada811.ghblog.domain.model.User
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.threeten.bp.ZonedDateTime

@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private val context: Context by lazy { InstrumentationRegistry.getTargetContext()!! }
    private lateinit var database: OrmaDatabase

    @Before
    fun setup() {
        database = OrmaDatabase.builder(context).name(null).trace(true).build()
    }

    @After
    fun teardown() {
    }

    @Test
    fun upsert() {
        val userResponseJson = """{"login":"wada811","id":1378923,"avatar_url":"https://avatars.githubusercontent.com/u/1378923?v=3","gravatar_id":"","url":"https://api.github.com/users/wada811","html_url":"https://github.com/wada811","followers_url":"https://api.github.com/users/wada811/followers","following_url":"https://api.github.com/users/wada811/following{/other_user}","gists_url":"https://api.github.com/users/wada811/gists{/gist_id}","starred_url":"https://api.github.com/users/wada811/starred{/owner}{/repo}","subscriptions_url":"https://api.github.com/users/wada811/subscriptions","organizations_url":"https://api.github.com/users/wada811/orgs","repos_url":"https://api.github.com/users/wada811/repos","events_url":"https://api.github.com/users/wada811/events{/privacy}","received_events_url":"https://api.github.com/users/wada811/received_events","type":"User","site_admin":false,"name":"wada811","company":null,"blog":"https://wada811.com/","location":null,"email":"at.wada811@gmail.com","hireable":true,"bio":null,"public_repos":42,"public_gists":22,"followers":31,"following":53,"created_at":"2012-01-25T12:04:25Z","updated_at":"2016-07-20T12:06:50Z","private_gists":0,"total_private_repos":0,"owned_private_repos":0,"disk_usage":177185,"collaborators":0,"plan":{"name":"free","space":976562499,"collaborators":0,"private_repos":0}}"""
        val user = createUser(userResponseJson)
        val userEntity1 = UserEntityDataMapper.fromUser(user)
        user.login = "update"
        val userEntity2 = UserEntityDataMapper.fromUser(user)
        database.relationOfUserEntity().upserter().execute(userEntity1)
        database.relationOfUserEntity().upserter().execute(userEntity2)
        val userEntities = database.selectFromUserEntity().toList()
        System.out.println("userEntities: $userEntities")
        assertEquals(1, userEntities.size)
    }

    private fun createUser(json: String): User {
        val moshi = Moshi.Builder().add(ZonedDateTime::class.java, ZonedDateTimeAdapter()).build()
        val jsonAdapter = moshi.adapter(GetUserResponse::class.java)
        val userResponse = jsonAdapter.fromJson(json)
        val user = GetUserResponseDataMapper.transform("access_token", userResponse)
        return user
    }

}