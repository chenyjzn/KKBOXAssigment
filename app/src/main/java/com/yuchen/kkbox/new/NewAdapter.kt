package com.yuchen.kkbox.new

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yuchen.kkbox.data.Album
import com.yuchen.kkbox.data.NewRelease
import com.yuchen.kkbox.databinding.HolderNewReleaseBinding
import com.yuchen.kkbox.databinding.HolderSongHorizontalBinding
import com.yuchen.kkbox.databinding.HolderTitleBinding
import com.yuchen.kkbox.time.convertDateToyyyyMMdd

class NewAdapter(private val viewModel: NewViewModel) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var featuredAlbumList: List<Album>? = null
    private var newReleaseList: List<NewRelease>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HEADER -> TitleHolder(HolderTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            NEW_RELEASE -> NewReleaseHolder(HolderNewReleaseBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            FEATURED_ALBUM -> SongHorizontalHolder(HolderSongHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    class TitleHolder(var binding: HolderTitleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            if (position == 0)
                binding.holderTitleText.text = "最近發行專輯"
            if (position == 2)
                binding.holderTitleText.text = "近期精選歌單"
            binding.executePendingBindings()
        }
    }

    class SongHorizontalHolder(var binding: HolderSongHorizontalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(album : Album) {
            var imgUrl: String?
            val imagesSize = album.images.size
            if (imagesSize==0){
                imgUrl = null
            }else{
                imgUrl = album.images[album.images.lastIndex].url
            }
            binding.imageUrl = imgUrl
            binding.holderSongHorizonalText1.text = album.title
            binding.holderSongHorizonalText2.text = "${album.owner.name}@${convertDateToyyyyMMdd(album.updatedAt)}"
            binding.executePendingBindings()
        }
    }

    class NewReleaseHolder(var binding: HolderNewReleaseBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(newReleaseList : List<NewRelease>, viewModel: NewViewModel) {
            val adapter = NewReleaseAdapter(viewModel)
            binding.holderNewReleaseRecycler.adapter = adapter
            binding.holderNewReleaseRecycler.layoutManager = LinearLayoutManager(binding.holderNewReleaseRecycler.context, LinearLayoutManager.HORIZONTAL,false)
            adapter.submitList(newReleaseList)
            binding.executePendingBindings()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> HEADER
            1 -> NEW_RELEASE
            2 -> HEADER
            else -> FEATURED_ALBUM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TitleHolder -> {
                holder.bind(position)
            }
            is NewReleaseHolder -> {
                newReleaseList?.let {
                    holder.bind(it,viewModel)
                }
            }
            is SongHorizontalHolder -> {
                featuredAlbumList?.let {
                    holder.bind(it[position-3])
                }
            }
        }
    }

    override fun getItemCount(): Int {
        featuredAlbumList?.let {
            return it.size + 3
        }
        return 3
    }

    fun submitFeaturedAlbumList(list: List<Album>) {
        featuredAlbumList = list
    }

    fun submitNewReleaseList(list: List<NewRelease>) {
        newReleaseList = list
    }

    companion object{
        const val HEADER = 0
        const val NEW_RELEASE = 1
        const val FEATURED_ALBUM = 2
    }
}