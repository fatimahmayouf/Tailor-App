package com.example.tailor.views.adaptersimport

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.example.tailor.R
import com.example.tailor.model.explore.Photo
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.example.tailor.databinding.ExploreItemLayoutBinding
import com.example.tailor.views.main.ExploreViewModel

private const val TAG = "ExploreAdapter"
class ExploreAdapter(val context: Context, val viewModel: ExploreViewModel) :
    RecyclerView.Adapter<ExploreAdapter.ExploreViewHolder>() {

    //val sharedView = SharedViewModel()

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            // check the item is same
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem == newItem // update content or not
        }
    }

    private  val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExploreAdapter.ExploreViewHolder {
        val binding= ExploreItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ExploreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExploreViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)
        Log.d(TAG,item.toString())

        holder.itemView.setOnClickListener {

            viewModel.exploreItemImg = item.src.medium
            viewModel.exploreItemPrice = 370.00

            it.findNavController().navigate(R.id.action_exploreFragment_to_orderFragment)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    fun submitList(list: List<Photo>){
        differ.submitList(list)
    }
    inner class ExploreViewHolder( val binding: ExploreItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Photo){
            Glide.with(context).load(item.src.large2x).into(binding.exploreImg)
        }

    }
}