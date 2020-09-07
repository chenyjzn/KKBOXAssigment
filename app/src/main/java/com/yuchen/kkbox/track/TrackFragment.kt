package com.yuchen.kkbox.track

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yuchen.kkbox.databinding.FragmentTrackBinding

class TrackFragment : Fragment() {
    lateinit var binding: FragmentTrackBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentTrackBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.trackWebView.loadUrl(TrackFragmentArgs.fromBundle(requireArguments()).track.url)
        return binding.root
    }
}

