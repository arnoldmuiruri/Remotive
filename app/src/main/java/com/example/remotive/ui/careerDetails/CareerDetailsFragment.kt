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

package com.example.remotive.ui.careerDetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.remotive.R
import com.example.remotive.databinding.FragmentCareerDetailsBinding
import org.jsoup.Jsoup


class CareerDetailsFragment : Fragment(R.layout.fragment_career_details) {

    private val args by navArgs<CareerDetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentCareerDetailsBinding.bind(view)

        binding.apply {

            val career = args.career

            val toolbar = binding.topBar
            toolbar.elevation = 0.0f

            toolbar.setNavigationOnClickListener {
                binding.root.findNavController().navigateUp()
            }

            val descriptionText = Jsoup.parse(career.description).text()
            val companyDescription = career.companyName + ", " + career.candidateRequiredLocation
            val jobType = "Job Type: " + career.jobType
            val salary = "Salary: " + career.salary

            Glide.with(this@CareerDetailsFragment)
                .load(career.companyLogoUrl)
                .error(R.drawable.logo)
                .into(imageViewCompanyLogo)

            textViewJobTitles.text = career.title
            textViewCompanyDescription.text = companyDescription
            textViewJobType.text = jobType
            textViewSalary.text = salary
            textViewDescription.text = descriptionText









        }


    }

}