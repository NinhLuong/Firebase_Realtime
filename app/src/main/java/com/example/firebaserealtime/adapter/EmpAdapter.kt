package com.example.firebaserealtime.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaserealtime.EmployeeModel
import com.example.firebaserealtime.R
import kotlinx.android.synthetic.main.emp_list_item.view.*

class EmpAdapter(private val ds:ArrayList<EmployeeModel>): RecyclerView.Adapter<EmpAdapter.ViewHolder>() {
//    code adapter lắng nghe sự kiện
    private lateinit var mListener: onItemClickListener
    interface onItemClickListener {
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

//    tao class viewholder
    class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener { clickListener.onItemClick(adapterPosition)}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        bien layouts thanh view holder
        val itemView =LayoutInflater.from(parent.context)
            .inflate(R.layout.emp_list_item,parent,false)
        return ViewHolder(itemView,mListener)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            tvEmpName.text = ds[position].empName
        }
    }

    override fun getItemCount(): Int {
        return ds.size
    }
}