package com.yuchen.kkbox.new

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yuchen.kkbox.MainActivity
import com.yuchen.kkbox.data.Auth
import com.yuchen.kkbox.databinding.FragmentNewBinding
import com.yuchen.kkbox.ext.getVmFactory
import com.yuchen.kkbox.factory.ViewModelAuthFactory
import com.yuchen.kkbox.home.HomeFragmentDirections
import com.yuchen.kkbox.network.LoadApiStatus

class NewFragment : Fragment() {
    lateinit var binding: FragmentNewBinding
    val viewModel: NewViewModel by viewModels {
        getVmFactory(
            arguments?.getParcelable<Auth>("Auth") ?: Auth()
        )
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentNewBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        val adapter = NewAdapter(viewModel)
        binding.newRecycler.adapter = adapter
        binding.newRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        viewModel.categoriesResult.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.data.isEmpty()||it.error!=null){
                    viewModel.setNewReleaseResultErr()
                }else{
                    viewModel.getNewReleaseAlbumsByCategories(it.data[0].id)
                }
                viewModel.setCategoriesResultDone()
            }
        })
        viewModel.newReleaseResult.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (!viewModel.isFeaturedResultNull()&&!viewModel.getIsFeaturedInit()&&!viewModel.getIsNewReleaseInit()) {
                    viewModel.initDataItemList()
                    viewModel.setNewReleaseResultDone()
                }
            }
        })
        viewModel.featuredResult.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (!viewModel.isNewReleaseResultNull()&&!viewModel.getIsFeaturedInit()&&!viewModel.getIsNewReleaseInit()){
                    viewModel.initDataItemList()
                }else if(viewModel.getIsFeaturedFromPaging()){
                    viewModel.setFeatured(it.data)
                }
                viewModel.nextPagingUrl = it.paging
                viewModel.setFeaturedResultDone()
            }
        })
        viewModel.dataItemList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
                adapter.notifyDataSetChanged()
            }
        })
        viewModel.loadApiStatus.observe(viewLifecycleOwner, Observer {
            it?.let {
                when (it) {
                    is LoadApiStatus.LOADING -> {
                        binding.newProgress.visibility = View.VISIBLE
                    }
                    is LoadApiStatus.DONE -> {
                        binding.newProgress.visibility = View.INVISIBLE
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
        binding.newRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val url = viewModel.hasPaging()
                if (url != null && !recyclerView.canScrollVertically(1) && !viewModel.isLoading()) {
                    viewModel.getFeaturedPaging(url)
                }
            }
        })
        return binding.root
    }
}