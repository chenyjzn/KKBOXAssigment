package com.yuchen.kkbox.new

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yuchen.kkbox.data.Data
import com.yuchen.kkbox.databinding.HolderSongHorizontalBinding
import com.yuchen.kkbox.databinding.HolderTitleBinding
import com.yuchen.kkbox.rank.RankViewModel
import java.lang.ClassCastException

//class RankAdapter(private val viewModel: RankViewModel) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//
//    private var dataList: List<Data>? = null
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        return when (viewType) {
//            HEADER -> TitleHolder(HolderTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false))
//            SONG -> SongHorizontalHolder(HolderSongHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false))
//            else -> throw ClassCastException("Unknown viewType $viewType")
//        }
//    }
//
//    class TitleHolder(var binding: HolderTitleBinding) : RecyclerView.ViewHolder(binding.root) {
//        fun bind(text:String) {
//            binding.holderTitleText.text = text
//            binding.executePendingBindings()
//        }
//    }
//
//    class SongHorizontalHolder(var binding: HolderSongHorizontalBinding) : RecyclerView.ViewHolder(binding.root) {
//        fun bind(data : Data) {
//            binding.executePendingBindings()
//        }
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        return when (position) {
//            0 -> HEADER
//            else -> SONG
//        }
//    }
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        when (holder) {
//            is TitleHolder -> {
//                holder.bind(productItem.title)
//            }
//            is SongHorizontalHolder -> {
//                val productItem = getItem(position) as DataItem.ProductItem1
//                holder.itemView.setOnClickListener {
//                    onClickListener?.onProductClick(productItem.product)
//                }
//                holder.bind(productItem.product)
//            }
//        }
//    }
//
//    override fun getItemCount(): Int {
//        dataList?.let {
//            return 1 + it.size
//        }
//        return 1
//    }
//
//    fun submitList(dataList: List<Data>) {
//        this.dataList = dataList
//    }
//
//    companion object{
//        const val HEADER = 0
//        const val SONG = 1
//    }
//}