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
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.remotive.databases.CareerDatabase
import com.example.remotive.models.entities.Career
import com.example.remotive.models.entities.RemoteKeys
import com.example.remotive.network.RemotiveService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val STARTING_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class RemotiveRemoteMediator @Inject constructor(
    private val service: RemotiveService,
    private val database: CareerDatabase
) : RemoteMediator<Int, Career>() {


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Career>
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try{

            val apiResponse = service.getCareerList()
            val careers = apiResponse.careers
            val endOfPaginationReached = careers.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.remoteKeysDao().clearRemoteKeys()
                    database.careersDao().clearCareers()
                }
                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = careers.map {
                    RemoteKeys(id = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                database.remoteKeysDao().saveKeys(keys)
                database.careersDao().saveCareers(careers)
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        }catch (exception: IOException){
            return MediatorResult.Error(exception)
        }catch (exception: HttpException){
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Career>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { career ->
                database.remoteKeysDao().getRemoteKeys(career.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Career>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { career ->
                database.remoteKeysDao().getRemoteKeys(career.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Career>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { careerId ->
                database.remoteKeysDao().getRemoteKeys(careerId)
            }
        }
    }


}
