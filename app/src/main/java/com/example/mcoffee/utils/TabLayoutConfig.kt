package com.example.mcoffee.utils

import com.google.android.material.tabs.TabLayout

object TabLayoutConfig {
    fun setUpTabLayout(tabLayout: TabLayout, tabItems: List<String>) {
        for (item in tabItems) {
            tabLayout.addTab(tabLayout.newTab().setText(item))
        }
    }
}