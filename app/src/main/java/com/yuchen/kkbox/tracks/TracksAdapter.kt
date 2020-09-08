package com.yuchen.kkbox.tracks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yuchen.kkbox.data.Album
import com.yuchen.kkbox.data.Track
import com.yuchen.kkbox.databinding.HolderCoverBinding
import com.yuchen.kkbox.databinding.HolderSongHorizontalBinding
import com.yuchen.kkbox.databinding.HolderTitleBinding

class TracksAdapter(private val viewModel: TracksViewModel) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var trackList: List<Track>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            COVER -> CoverHolder(HolderCoverBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            TITLE -> TitleHolder(HolderTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            TRACK -> SongHorizontalHolder(HolderSongHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    class TitleHolder(var binding: HolderTitleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.holderTitleText.text = "歌曲列表"
            binding.executePendingBindings()
        }
    }

    class SongHorizontalHolder(var binding: HolderSongHorizontalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(track: Track,viewModel: TracksViewModel) {
            val album = track.album?:viewModel.album
            binding.imgUrl = album.displayCover
            binding.holderSongHorizonalText1.text = track.name
            binding.holderSongHorizonalText2.text = "${album.displayArtist}@${album.displayDate}"
            binding.holderSongVerticalImage.setOnClickListener {
                viewModel.setNavigateToTrack(track)
            }
            binding.executePendingBindings()
        }
    }

    class CoverHolder(var binding: HolderCoverBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imgUrl: String?) {
            binding.imgUrl = imgUrl
            binding.executePendingBindings()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> COVER
            1 -> TITLE
            else -> TRACK
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CoverHolder -> {
                trackList?.let {
                    holder.bind(viewModel.album.displayCover)
                }
            }
            is TitleHolder -> {
                holder.bind()
            }
            is SongHorizontalHolder -> {
                trackList?.let {
                    holder.bind(it[position-2],viewModel)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        trackList?.let {
            return it.size + 2
        }
        return 2
    }

    fun submitList(list: List<Track>) {
        trackList = list
    }

    companion object{
        const val COVER = 0
        const val TITLE = 1
        const val TRACK = 2
    }
}