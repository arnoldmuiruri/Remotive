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

package com.example.remotive.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.remotive.models.entities.Career
import com.example.remotive.models.entities.Converter
import com.example.remotive.models.entities.RemoteKeys

//Persistence class for the careers and
// remote keys entities class

@Database(
    entities = [Career::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(Converter::class)
abstract class CareerDatabase: RoomDatabase() {

    abstract fun careersDao(): CareersDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}