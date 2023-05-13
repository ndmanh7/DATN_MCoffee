package com.example.mcoffee.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mcoffee.ui.fragment.viewpager.AbortedOrderFragment
import com.example.mcoffee.ui.fragment.viewpager.ConfirmedOrderFragment
import com.example.mcoffee.ui.fragment.viewpager.NotConfirmedOrderFragment

class ViewPagerAdapter(fragment: Fragment, private var totalCount: Int) : FragmentStateAdapter(fragment){
    override fun getItemCount(): Int = totalCount

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> NotConfirmedOrderFragment()
            1 -> ConfirmedOrderFragment()
            2 -> AbortedOrderFragment()
            else -> NotConfirmedOrderFragment()
        }
    }


}