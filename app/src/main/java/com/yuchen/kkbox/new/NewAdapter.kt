package com.yuchen.kkbox.new

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yuchen.kkbox.data.Album
import com.yuchen.kkbox.data.DataItem
import com.yuchen.kkbox.data.DataItem.Companion.FEATURED
import com.yuchen.kkbox.data.DataItem.Companion.NEW_RELEASE_LIST
import com.yuchen.kkbox.data.DataItem.Companion.TITLE
import com.yuchen.kkbox.databinding.HolderNewReleaseBinding
import com.yuchen.kkbox.databinding.HolderSongHorizontalBinding
import com.yuchen.kkbox.databinding.HolderTitleBinding

class NewAdapter(private val viewModel: NewViewModel) : ListAdapter<DataItem, RecyclerView.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TITLE -> TitleHolder(HolderTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            NEW_RELEASE_LIST -> NewReleaseHolder(HolderNewReleaseBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            FEATURED -> SongHorizontalHolder(HolderSongHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    class TitleHolder(var binding: HolderTitleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(title: String) {
            binding.holderTitleText.text = title
            binding.executePendingBindings()
        }
    }

    class SongHorizontalHolder(var binding: HolderSongHorizontalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(album : Album, viewModel: NewViewModel) {
            binding.imgUrl = album.displayCover
            binding.holderSongHorizonalText1.text = album.displayTitle
            binding.holderSongHorizonalText2.text = "${album.displayArtist}@${album.displayDate}"
            binding.executePendingBindings()
        }
    }

    class NewReleaseHolder(var binding: HolderNewReleaseBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(newReleaseList : List<Album>, viewModel: NewViewModel) {
            val adapter = NewReleaseAdapter(viewModel)
            binding.holderNewReleaseRecycler.adapter = adapter
            binding.holderNewReleaseRecycler.layoutManager = LinearLayoutManager(binding.holderNewReleaseRecycler.context, LinearLayoutManager.HORIZONTAL,false)
            binding.holderNewReleaseRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(0)) {

                    }
                }
            })
            adapter.submitList(newReleaseList)
            binding.executePendingBindings()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Title -> TITLE
            is DataItem.NewReleaseList -> NEW_RELEASE_LIST
            is DataItem.Featured -> FEATURED
            else -> throw IllegalArgumentException("wrong data item type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TitleHolder -> {
                holder.bind((getItem(position) as DataItem.Title).title)
            }
            is NewReleaseHolder -> {
                holder.bind((getItem(position) as DataItem.NewReleaseList).albums,viewModel)
            }
            is SongHorizontalHolder -> {
                val album = (getItem(position) as DataItem.Featured).album
                holder.bind(album,viewModel)
                holder.itemView.setOnClickListener {
                    viewModel.setNavigateToTracks(album)
                }
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }
    }
}