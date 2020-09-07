package com.yuchen.kkbox.rank

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yuchen.kkbox.data.Album
import com.yuchen.kkbox.databinding.HolderSongHorizontalBinding
import com.yuchen.kkbox.time.convertUpdateAtToyyyyMMdd

class RankAdapter(private val viewModel: RankViewModel) : RecyclerView.Adapter<RankAdapter.SongHorizontalHolder>() {

    private var albumList: List<Album>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongHorizontalHolder {
        return SongHorizontalHolder(HolderSongHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    inner class SongHorizontalHolder(var binding: HolderSongHorizontalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(album : Album,viewModel: RankViewModel) {
            binding.album = album
            binding.holderSongHorizonalText1.text = album.displayTitle
            binding.holderSongHorizonalText2.text = "${album.displayArtist}@${album.displayDate}"
            binding.holderSongVerticalImage.setOnClickListener {
                viewModel.setNavigateToTracks(album)
            }
            binding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: SongHorizontalHolder, position: Int) {
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