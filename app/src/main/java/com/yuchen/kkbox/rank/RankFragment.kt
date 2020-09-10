package com.yuchen.kkbox.rank

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yuchen.kkbox.MainActivity
import com.yuchen.kkbox.data.Auth
import com.yuchen.kkbox.databinding.FragmentRankBinding
import com.yuchen.kkbox.ext.getVmFactory
import com.yuchen.kkbox.factory.ViewModelAuthFactory
import com.yuchen.kkbox.home.HomeFragmentDirections
import com.yuchen.kkbox.network.LoadApiStatus

class RankFragment : Fragment() {
    lateinit var binding: FragmentRankBinding
    val viewModel: RankViewModel by viewModels{ getVmFactory(arguments?.getParcelable<Auth>("Auth")?:Auth()) }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentRankBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        val adapter = RankAdapter(viewModel)
        binding.rankRecycler.adapter = adapter
        binding.rankRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        viewModel.rankResult.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it.data)
                adapter.notifyDataSetChanged()
            }
        })
        viewModel.loadApiStatus.observe(viewLifecycleOwner, Observer {
            it?.let {
                when (it) {
                    is LoadApiStatus.LOADING -> {
                        binding.rankProgress.visibility = View.VISIBLE
                    }
                    is LoadApiStatus.DONE -> {
                        binding.rankProgress.visibility = View.INVISIBLE
                    }
                    is LoadApiStatus.ERROR -> {
                        (activity as MainActivity).showErrorMessage(it.message)
                    }
                }
            }
        })
        viewModel.navigateToTracks.observe(viewLifecycleOwner, Observer {
            it?.let {
                parentFragment?.findNavController()?.navigate(HomeFragmentDirections.actionHomeFragmentToTracksFragment(it,arguments?.getParcelable<Auth>("Auth")?:Auth()))
                viewModel.navigateToTracksDone()
            }
        })
        Log.d("chenyjzn","rank auth${arguments?.getParcelable<Auth>("Auth")?:Auth()}")
        return binding.root
    }
}