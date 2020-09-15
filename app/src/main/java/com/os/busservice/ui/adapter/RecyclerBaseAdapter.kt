package com.os.busservice.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.adapters.ViewGroupBindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView


abstract class RecyclerBaseAdapter : RecyclerView.Adapter<RecyclerBaseAdapter.RecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder=
     RecyclerViewHolder(
         DataBindingUtil.inflate<ViewDataBinding>(
             LayoutInflater.from(parent.context),viewType,parent,false
         )
     )

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val obj:Any? =getViewModel(position)
        holder.bindObj(obj)
        putViewDataBinding(holder.binding)
    }
    override fun getItemViewType(position: Int) = getLayoutIdForPosition(position)
    abstract fun getLayoutIdForPosition(position: Int): Int
    abstract fun putViewDataBinding(binding: ViewDataBinding)
    abstract fun getViewModel(position: Int): Any?


    open class RecyclerViewHolder(val binding: ViewDataBinding):RecyclerView.ViewHolder(binding.root){
      fun bindObj(obj:Any?){
         when(obj){
          /*   is CatStoreResult ->{
                 binding.setVariable(BR.mHomeDataItem,obj)
                 binding.setVariable(BR.mStoreCatResult,obj)
             }
             is StoreResult -> binding.setVariable(BR.mStoreCat,obj)
             is CartItem -> binding.setVariable(BR.mItem,obj)
             is AddressResult -> binding.setVariable(BR.mItem,obj)
             is CompletedOrder -> binding.setVariable(BR.mItem,obj)*/


         }
      }

    }

}