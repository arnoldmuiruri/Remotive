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

package com.example.remotive.ui.careerList

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.remotive.R
import com.example.remotive.databinding.CareerItemBinding
import com.example.remotive.models.entities.Career
import com.example.remotive.utils.DiffUtilCallBack

class RemotiveAdapter(private val listener: OnItemClickListener) : PagingDataAdapter<Career,
        RemotiveAdapter.ViewHolder>(DiffUtilCallBack()) {


    inner class ViewHolder(private val binding: CareerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            //Todo("Add functionality to this")
            val bookmarkButton = itemView.findViewById<ImageView>(R.id.bookmark_icon)
            bookmarkButton.setOnClickListener {
                bookmarkButton.isSelected = !bookmarkButton.isSelected

            }

            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onItemClick(item)
                    }

                }
            }
        }

        fun bind(career: Career) {
            binding.apply {
                textViewJobTitle.text = career.title
                textViewCompany.text = career.companyName

                //Loading the company logo
                Glide.with(itemView)
                    .load(career.companyLogoUrl)
                    .centerCrop()
                    .error(R.drawable.remotivelogo)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageViewCompanyLogo)

            }
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = CareerItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    interface OnItemClickListener {
        fun onItemClick(career: Career)
    }


}