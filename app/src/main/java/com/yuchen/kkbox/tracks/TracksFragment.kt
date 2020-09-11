package com.yuchen.kkbox.tracks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.yuchen.kkbox.MainActivity
import com.yuchen.kkbox.databinding.FragmentTracksBinding
import com.yuchen.kkbox.ext.getVmFactory
import com.yuchen.kkbox.network.LoadApiStatus

class TracksFragment : Fragment() {
    lateinit var binding: FragmentTracksBinding
    val viewModel: TracksViewModel by viewModels {
        getVmFactory(
            TracksFragmentArgs.fromBundle(requireArguments()).auth,
            TracksFragmentArgs.fromBundle(requireArguments()).album
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentTracksBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        val adapter = TracksAdapter(viewModel)
        binding.tracksRecycler.adapter = adapter
        binding.tracksRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)

        viewModel.tracksResult.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it.data)
                adapter.notifyDataSetChanged()
            }
        })

        viewModel.loadApiStatus.observe(viewLifecycleOwner, Observer {
            it?.let {
                when (it) {
                    is LoadApiStatus.LOADING -> {
                        binding.tracksProgress.visibility = View.VISIBLE
                    }
                    is LoadApiStatus.DONE -> {
                        binding.tracksProgress.visibility = View.INVISIBLE
                    }
                    is LoadApiStatus.ERROR -> {
                        (activity as MainActivity).showErrorMessage(it.message)
                    }
                }
            }
        })

        viewModel.navigateToTrack.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(TracksFragmentDirections.actionTracksFragmentToTrackFragment(it))
                viewModel.navigateToTracksDone()
            }
        })
        return binding.root
    }
}
