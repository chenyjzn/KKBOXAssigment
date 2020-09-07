package com.yuchen.kkbox.new

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yuchen.kkbox.data.NewRelease
import com.yuchen.kkbox.databinding.HolderSongVerticalBinding

class NewReleaseAdapter(private val viewModel: NewViewModel) : RecyclerView.Adapter<NewReleaseAdapter.SongVerticalHolder>() {

    private var newReleaseList: List<NewRelease>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongVerticalHolder {
        return SongVerticalHolder(HolderSongVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    inner class SongVerticalHolder(var binding: HolderSongVerticalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(newRelease : NewRelease) {
            var imgUrl: String?
            val imagesSize = newRelease.images.size
            if (imagesSize==0){
                imgUrl = null
            }else{
                imgUrl = newRelease.images[newRelease.images.lastIndex].url
            }
            binding.imageUrl = imgUrl
            binding.holderSongVerticalText.text = newRelease.name
            binding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: SongVerticalHolder, position: Int) {
        newReleaseList?.let {
            holder.bind(it[position])
        }
    }

    override fun getItemCount(): Int {
        newReleaseList?.let {
            return it.size
        }
        return 0
    }

    fun submitList(albumList: List<NewRelease>) {
        this.newReleaseList = albumList
    }
}