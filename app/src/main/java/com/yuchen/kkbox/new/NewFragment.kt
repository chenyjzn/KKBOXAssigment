package com.yuchen.kkbox.new

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.yuchen.kkbox.data.Auth
import com.yuchen.kkbox.databinding.FragmentNewBinding
import com.yuchen.kkbox.factory.ViewModelAuthFactory
import com.yuchen.kkbox.factory.ViewModelFactory

class NewFragment : Fragment() {
    lateinit var binding: FragmentNewBinding
    val viewModel: NewViewModel by viewModels{ ViewModelAuthFactory(arguments?.getParcelable<Auth>("Auth")?:Auth()) }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentNewBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
}