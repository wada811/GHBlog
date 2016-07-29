package com.wada811.ghblog.data.datasource.factory

import com.wada811.ghblog.data.datasource.user.CloudUserDataSource
import com.wada811.ghblog.data.datasource.user.DatabaseUserDataSource
import com.wada811.ghblog.data.datasource.user.UserDataSource

interface IUserDataSourceFactory {
    fun createDataSource(): UserDataSource
    fun createDatabaseDataSource(): DatabaseUserDataSource
    fun createCloudDataSource(): CloudUserDataSource
}
