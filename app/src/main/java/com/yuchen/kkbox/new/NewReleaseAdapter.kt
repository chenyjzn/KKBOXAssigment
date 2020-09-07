package com.yuchen.kkbox.new

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yuchen.kkbox.data.Album
import com.yuchen.kkbox.databinding.HolderSongVerticalBinding

class NewReleaseAdapter(private val viewModel: NewViewModel) : RecyclerView.Adapter<NewReleaseAdapter.SongVerticalHolder>() {

    private var albumList: List<Album>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongVerticalHolder {
        return SongVerticalHolder(HolderSongVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    inner class SongVerticalHolder(var binding: HolderSongVerticalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(album : Album,viewModel: NewViewModel) {
            binding.album = album
            binding.holderSongVerticalText.text = album.displayTitle
            binding.holderSongVerticalImage.setOnClickListener {
                viewModel.setNavigateToTracks(album)
            }
            binding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: SongVerticalHolder, position: Int) {
        albumList?.let {
            holder.bind(it[position],viewModel)
        }
    }

    override fun getItemCount(): Int {
        albumList?.let {
            return it.size
        }
        return 0
    }

    fun submitList(albumList: List<Album>) {
        this.albumList = albumList
    }
}