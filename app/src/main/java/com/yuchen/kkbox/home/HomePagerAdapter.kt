package com.yuchen.kkbox.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.yuchen.kkbox.data.Auth
import com.yuchen.kkbox.new.NewFragment
import com.yuchen.kkbox.rank.RankFragment

class HomePagerAdapter(fm: FragmentManager,private val bundle: Bundle) : FragmentStatePagerAdapter(fm) {
    override fun getCount(): Int = 2
    override fun getItem(i: Int): Fragment {
        val newFragment = NewFragment()
        val rankFragment = RankFragment()
        newFragment.arguments = bundle
        rankFragment.arguments = bundle
        return when(i){
            0 -> newFragment
            1 -> rankFragment
            else -> throw IllegalAccessError("Out of pager pos")
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "新發行"
            1 -> "排行榜"
            else -> throw IllegalAccessError("Out of pager pos")
        }
    }

}