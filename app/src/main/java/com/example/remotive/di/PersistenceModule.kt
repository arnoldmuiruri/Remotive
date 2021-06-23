/*
 * Copyright (c) 2021 Arnold Muiruri
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.remotive.di

import android.app.Application
import androidx.room.Room
import com.example.remotive.databases.CareerDatabase
import com.example.remotive.databases.CareersDao
import com.example.remotive.databases.RemoteKeysDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Provides
    @Singleton
    fun provideDatabase(
        application: Application
    ): CareerDatabase = Room
        .databaseBuilder(
            application,
            CareerDatabase::class.java,
            "careers.db"
        ).fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideCareersDao(database: CareerDatabase)
            : CareersDao = database.careersDao()

    @Provides
    @Singleton
    fun provideRemoteKeysDao(database: CareerDatabase)
            : RemoteKeysDao = database.remoteKeysDao()
}