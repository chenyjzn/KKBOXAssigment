package com.yuchen.kkbox.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
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
        homePagerAdapter = HomePagerAdapter(childFragmentManager,args.toBundle(),homePagerArray)
        binding.homeTab.setupWithViewPager(binding.homeViewPager)
        binding.homeViewPager.adapter = homePagerAdapter
        return binding.root
    }
}
