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

package com.example.remotive.utils

import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView

object BottomNavigationUtils {

    fun showBottomNav(bottomNavigationView: BottomNavigationView){
        bottomNavigationView.visibility = View.VISIBLE
    }
    fun hideBottomNav(bottomNavigationView: BottomNavigationView){
        bottomNavigationView.visibility  = View.INVISIBLE
    }
}