package com.yuchen.kkbox.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.yuchen.kkbox.data.Auth
import com.yuchen.kkbox.new.NewFragment
import com.yuchen.kkbox.rank.RankFragment

class HomePagerAdapter(fm: FragmentManager,private val bundle: Bundle,private val homePagerArray:Array<String>) : FragmentStatePagerAdapter(fm) {
    override fun getCount(): Int = PAGER_SIZE
    override fun getItem(i: Int): Fragment {
        val newFragment = NewFragment()
        val rankFragment = RankFragment()
        newFragment.arguments = bundle
        rankFragment.arguments = bundle
        return when(i){
            NEW -> newFragment
            RANK -> rankFragment
            else -> throw IllegalAccessError("Out of pager pos")
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            NEW -> homePagerArray[NEW]
            RANK -> homePagerArray[RANK]
            else -> throw IllegalAccessError("Out of pager pos")
        }
    }

    companion object{
        const val NEW = 0
        const val RANK = 1
        const val PAGER_SIZE = 2
    }
}