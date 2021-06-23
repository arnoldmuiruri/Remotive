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

package com.example.remotive.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.remotive.databases.CareerDatabase
import com.example.remotive.models.entities.Career
import com.example.remotive.network.RemotiveService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CareerRepositoryImpl @Inject constructor(
    val remotiveService: RemotiveService,
    val database: CareerDatabase
) : CareerRepository {

    fun getCareers(): Flow<PagingData<Career>> {

        val pagingDataSource = { database.careersDao().getCareers() }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = RemotiveRemoteMediator(remotiveService, database),
            pagingSourceFactory = pagingDataSource
        ).flow
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}