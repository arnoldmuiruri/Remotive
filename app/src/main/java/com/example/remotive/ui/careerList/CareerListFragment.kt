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

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.remotive.R
import com.example.remotive.databinding.FragmentCareerListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CareerListFragment : Fragment(R.layout.fragment_career_list) {

    private val viewModel by viewModels<CareerListViewModel>()

    private var _binding: FragmentCareerListBinding? = null
    private val binding get() = _binding!!
    private val adapter = RemotiveAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCareerListBinding.bind(view)

        //TODO("Add load state adapter")

        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.itemAnimator = null
            recyclerView.adapter = adapter
        }

        fetchPosts()

    }

    private fun fetchPosts() {
        lifecycleScope.launch {
            viewModel.careers.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

}