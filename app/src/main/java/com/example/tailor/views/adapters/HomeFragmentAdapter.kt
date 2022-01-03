package com.example.tailor.views.adaptersimport

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.example.tailor.R
import com.example.tailor.databinding.HomeItemLayoutBinding
import com.example.tailor.model.home.HomePhotoModel
import com.example.tailor.views.main.HomeViewModel

private const val TAG = "HomeFragmentAdapter"

class HomeFragmentAdapter(val context: Context, val viewModel: HomeViewModel) :
    RecyclerView.Adapter<HomeFragmentAdapter.HomeFragmentViewHolder>() {

    //val viewModel = SharedViewModel()

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HomePhotoModel>() {
        override fun areItemsTheSame(oldItem: HomePhotoModel, newItem: HomePhotoModel): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: HomePhotoModel, newItem: HomePhotoModel): Boolean {
            return oldItem == newItem // update content or not
        }
    }
    private  val differ = AsyncListDiffer(this, DIFF_CALLBACK)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeFragmentAdapter.HomeFragmentViewHolder {
        val binding= HomeItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HomeFragmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeFragmentViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)
        Log.d(TAG,item.toString())

        holder.itemView.setOnClickListener {
            viewModel.homeItemImg = item.url1
            viewModel.homeItemPrice = item.price.toDouble()

            it.findNavController().navigate(R.id.action_homeFragment_to_homeOrderFragment)

            //viewModel.exploreItem = item.id
           // it.findNavController().navigate(R.id.action_homeFragment_to_orderFragment)

            /*val orderFragment = OrderFragment()
            val activity = it.context as AppCompatActivity
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.home_layout,orderFragment)
                .commit()*/
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    fun submitList(list: List<HomePhotoModel>){
        differ.submitList(list)
    }
   inner class HomeFragmentViewHolder(val binding: HomeItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
       fun bind(item: HomePhotoModel){
           Glide.with(context).load(item.url1).into(binding.homeImageView)
       }
    }
}