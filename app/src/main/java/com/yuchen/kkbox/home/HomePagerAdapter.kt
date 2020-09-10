package com.yuchen.kkbox.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yuchen.kkbox.new.NewFragment
import com.yuchen.kkbox.rank.RankFragment

class HomePagerAdapter(
    fragment: Fragment,
    private val bundle: Bundle,
    private val homePagerArray: Array<String>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = homePagerArray.size

    override fun createFragment(position: Int): Fragment {
        val newFragment = NewFragment()
        val rankFragment = RankFragment()
        newFragment.arguments = bundle
        rankFragment.arguments = bundle
        return when (position) {
            NEW -> newFragment
            RANK -> rankFragment
            else -> throw IllegalAccessError("Out of pager pos")
        }
    }

    companion object {
        const val NEW = 0
        const val RANK = 1
    }
}