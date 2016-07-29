package com.wada811.ghblog

import com.wada811.ghblog.data.datasource.factory.IUserDataSourceFactory
import com.wada811.ghblog.data.datasource.user.CloudUserDataSource
import com.wada811.ghblog.data.datasource.user.DatabaseUserDataSource
import com.wada811.ghblog.data.datasource.user.UserDataSource

class TestUserDataSourceFactory: IUserDataSourceFactory {
    override fun createDataSource(): UserDataSource = CloudUserDataSource()

    override fun createDatabaseDataSource(): DatabaseUserDataSource {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createCloudDataSource(): CloudUserDataSource = CloudUserDataSource()
}