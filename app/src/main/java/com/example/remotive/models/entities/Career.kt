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

package com.example.remotive.models.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Persistence data class of remote jobs
 */
@Parcelize
@Entity(tableName = "careers")
data class Career(
    @PrimaryKey
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("url")
    val url: String,
    @field:SerializedName("title")
    val title: String,
    @field:SerializedName("company_name")
    val companyName: String,
    @field:SerializedName("category")
    val category: String,
    @field:SerializedName("job_type")
    val jobType: String?,
    @field:SerializedName("candidate_required_location")
    val candidateRequiredLocation: String,
    @field:SerializedName("company_logo_url")
    val companyLogoUrl: String?,
    @field:SerializedName("description")
    val description: String?,
    @field:SerializedName("salary")
    val salary: String?
) : Parcelable