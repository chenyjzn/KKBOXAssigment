package com.yuchen.kkbox.rank

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yuchen.kkbox.data.Data
import com.yuchen.kkbox.databinding.HolderSongHorizontalBinding
import com.yuchen.kkbox.time.convertDateToyyyyMMdd

class RankAdapter(private val viewModel: RankViewModel) : RecyclerView.Adapter<RankAdapter.SongHorizontalHolder>() {

    private var dataList: List<Data>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongHorizontalHolder {
        return SongHorizontalHolder(HolderSongHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    inner class SongHorizontalHolder(var binding: HolderSongHorizontalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data : Data) {
            var imgUrl: String?
            val imagesSize = data.images.size
            if (imagesSize==0){
                imgUrl = null
            }else{
                imgUrl = data.images[data.images.lastIndex].url
            }
            binding.imageUrl = imgUrl
            binding.holderSongHorizonalText1.text = data.title
            binding.holderSongHorizonalText2.text = "${data.owner.name}@${convertDateToyyyyMMdd(data.updatedAt)}"
            binding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: SongHorizontalHolder, position: Int) {
        dataList?.let {
            holder.bind(it[position])
        }
    }

    override fun getItemCount(): Int {
        dataList?.let {
            return it.size
        }
        return 0
    }

    fun submitList(dataList: List<Data>) {
        this.dataList = dataList
    }
}