package com.example.tailor.views.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tailor.databinding.OrdersItemLayoutBinding
import com.example.tailor.model.user.Orders
import com.example.tailor.views.user.ProfileViewModel


private const val TAG = "OrderListAdapter"
class OrderListAdapter(val context: Context, val viewModel: ProfileViewModel) : RecyclerView.Adapter<OrderListAdapter.OrderListViewHolder>(){

    val DiffUtil = object : DiffUtil.ItemCallback<Orders>(){
        override fun areItemsTheSame(oldItem: Orders, newItem: Orders): Boolean {
            return oldItem.orderImg == newItem.orderImg
        }

        override fun areContentsTheSame(oldItem: Orders, newItem: Orders): Boolean {
           return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this,DiffUtil)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderListAdapter.OrderListViewHolder {
        val binding = OrdersItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return OrderListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderListViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)
        Log.d(TAG,item.toString())
        when(position % 2 == 0){
            true -> {holder.itemView.setBackgroundColor(Color.WHITE)}
            false -> holder.itemView.setBackgroundColor(android.R.color.background_light.toInt())
        }

        holder.binding.orderListCancelButton.setOnClickListener {
            Toast.makeText(context, "your order has been deleted", Toast.LENGTH_SHORT).show()
            Log.d(TAG,position.toString())

            val difList = mutableListOf<Orders>()
            difList.addAll(differ.currentList)
            //viewModel.deleteUserOrder(item.docId)
            viewModel.deleteUserOrder(difList[position].docId)
            difList.removeAt(position)
            differ.submitList(difList)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    fun submitList(list: List<Orders>){
        differ.submitList(list)
    }

    inner class OrderListViewHolder(val binding: OrdersItemLayoutBinding):RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(item: Orders){
            binding.orderDateTxt.text = item.orderDate
            binding.orderPriceTxt.text = item.orderPrice.toString()
            binding.OrderIDTxt.text = itemId.toString()
            binding.orderNoteTxt.text = item.orderNotes

            if(item.orderApproval == null){
                binding.approvalTxt.text = "Waiting for approval ..."
               // binding.approvalTxt.setBackgroundColor(Color.BLUE)
                binding.approvalTxt.setTextColor(Color.BLUE)


            }else if(item.orderApproval == true){
                 binding.approvalTxt.text = " Approved"
                 binding.approvalTxt.setBackgroundColor(Color.GREEN)

            }else{
                binding.approvalTxt.text = "Rejected"
                binding.approvalTxt.setBackgroundColor(Color.RED)
            }
            Glide.with(context).load(item.orderImg).into(binding.orderListImgView)
        }
    }

}