package com.yuchen.kkbox.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.yuchen.kkbox.R

import com.yuchen.kkbox.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    lateinit var binding:FragmentHomeBinding
    private lateinit var homePagerAdapter: HomePagerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater)
        val args by navArgs<HomeFragmentArgs>()
        val homePagerArray = resources.getStringArray(R.array.home_pager)
        binding.homeViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        homePagerAdapter = HomePagerAdapter(this,args.toBundle(),homePagerArray)
        binding.homeViewPager.adapter = homePagerAdapter
        TabLayoutMediator(binding.homeTab, binding.homeViewPager) { tab, position ->
            tab.text = when (position) {
                0 -> homePagerArray[0]
                1 -> homePagerArray[1]
                else -> null
            }
        }.attach()
        return binding.root
    }
}
