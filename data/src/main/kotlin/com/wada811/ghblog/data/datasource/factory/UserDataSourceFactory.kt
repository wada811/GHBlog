package com.wada811.ghblog.data.datasource.factory

import com.wada811.ghblog.data.datasource.user.CloudUserDataSource
import com.wada811.ghblog.data.datasource.user.DatabaseUserDataSource
import com.wada811.ghblog.data.datasource.user.UserDataSource
import com.wada811.ghblog.data.entity.data.OrmaDatabase

class UserDataSourceFactory(private val database: OrmaDatabase) : IUserDataSourceFactory {
    override fun createDataSource(): UserDataSource {
        return CloudUserDataSource()
    }

    override fun createDatabaseDataSource(): DatabaseUserDataSource {
        return DatabaseUserDataSource(database)
    }

    override fun createCloudDataSource(): CloudUserDataSource {
        return CloudUserDataSource()
    }
}